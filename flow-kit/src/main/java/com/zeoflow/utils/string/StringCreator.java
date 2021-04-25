/*
 * Copyright (C) 2021 ZeoFlow SRL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zeoflow.utils.string;

import com.zeoflow.zson.Zson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;

import static com.zeoflow.utils.string.Util.checkArgument;

@SuppressWarnings({"unused", "RedundantSuppression"})
public final class StringCreator
{

    private static final Pattern NAMED_ARGUMENT =
            Pattern.compile("\\$(" + Pattern.quote("?<argumentName>") + "[\\w_]+):(" + Pattern.quote("?<typeChar>") + "[\\w]).*");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]+[\\w_]*");

    /**
     * A heterogeneous list containing string literals and value placeholders.
     */
    final List<String> formatParts;
    final List<Object> args;

    private StringCreator(Creator creator)
    {
        this.formatParts = Util.immutableList(creator.formatParts);
        this.args = Util.immutableList(creator.args);
    }
    public static StringCreator of(String format, Object... args)
    {
        return new Creator().add(format, args).create();
    }
    /**
     * A {@link Collector} implementation that joins {@link StringCreator} instances together into one
     * separated by {@code separator}. For example, joining {@code String s}, {@code Object o} and
     * {@code int i} using {@code ", "} would produce {@code String s, Object o, int i}.
     */
    public static Collector<StringCreator, ?, StringCreator> joining(String separator)
    {
        return Collector.of(
                () -> new CodeBlockJoiner(separator, creator()),
                CodeBlockJoiner::add,
                CodeBlockJoiner::merge,
                CodeBlockJoiner::join);
    }
    /**
     * A {@link Collector} implementation that joins {@link StringCreator} instances together into one
     * separated by {@code separator}. For example, joining {@code String s}, {@code Object o} and
     * {@code int i} using {@code ", "} would produce {@code String s, Object o, int i}.
     */
    public static Collector<StringCreator, ?, StringCreator> joining(
            String separator, String prefix, String suffix)
    {
        Creator creator = creator().add("$N", prefix);
        return Collector.of(
                () -> new CodeBlockJoiner(separator, creator),
                CodeBlockJoiner::add,
                CodeBlockJoiner::merge,
                joiner ->
                {
                    creator.add(StringCreator.of("$N", suffix));
                    return joiner.join();
                });
    }
    public static Creator creator()
    {
        return new Creator();
    }
    public boolean isEmpty()
    {
        return formatParts.isEmpty();
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return toString().equals(o.toString());
    }
    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        try
        {
            new StringWriter(out).emit(this);
            return out.toString();
        } catch (IOException e)
        {
            return "StringCreator: " + formatParts;
        }
    }
    public String asString()
    {
        return toString();
    }
    public String asJson()
    {
        return new Zson().toJson(toString());
    }
    public Creator toBuilder()
    {
        Creator creator = new Creator();
        creator.formatParts.addAll(formatParts);
        creator.args.addAll(args);
        return creator;
    }
    @SuppressWarnings("UnusedReturnValue")
    public static final class Creator
    {

        final List<String> formatParts = new ArrayList<>();
        final List<Object> args = new ArrayList<>();

        private Creator()
        {
        }

        public boolean isEmpty()
        {
            return formatParts.isEmpty();
        }

        /**
         * Adds code using named arguments.
         *
         * <p>Named arguments specify their name after the '$' followed by : and the corresponding type
         * character. Argument names consist of characters in {@code a-z, A-Z, 0-9, and _} and must
         * start with a lowercase character.
         *
         * <p>For example, to refer to the type {@link Integer} with the argument name {@code
         * clazz} use a format string containing {@code $clazz:T} and include the key {@code clazz} with
         * value {@code java.lang.Integer.class} in the argument map.
         */
        public Creator addNamed(String format, Map<String, ?> arguments)
        {
            int p = 0;

            for (String argument : arguments.keySet())
            {
                checkArgument(LOWERCASE.matcher(argument).matches(),
                        "argument '%s' must start with a lowercase character", argument);
            }

            while (p < format.length())
            {
                int nextP = format.indexOf("$", p);
                if (nextP == -1)
                {
                    formatParts.add(format.substring(p));
                    break;
                }

                if (p != nextP)
                {
                    formatParts.add(format.substring(p, nextP));
                    p = nextP;
                }

                Matcher matcher = null;
                int colon = format.indexOf(':', p);
                if (colon != -1)
                {
                    int endIndex = Math.min(colon + 2, format.length());
                    matcher = NAMED_ARGUMENT.matcher(format.substring(p, endIndex));
                }
                if (matcher != null && matcher.lookingAt())
                {
                    String argumentName = matcher.group("argumentName");
                    checkArgument(arguments.containsKey(argumentName), "Missing named argument for $%s",
                            argumentName);
                    char formatChar = matcher.group("typeChar").charAt(0);
                    addArgument(format, formatChar, arguments.get(argumentName));
                    formatParts.add("$" + formatChar);
                    p += matcher.regionEnd();
                } else
                {
                    checkArgument(p < format.length() - 1, "dangling $ at end");
                    checkArgument(isNoArgPlaceholder(format.charAt(p + 1)),
                            "unknown format $%s at %s in '%s'", format.charAt(p + 1), p + 1, format);
                    formatParts.add(format.substring(p, p + 2));
                    p += 2;
                }
            }

            return this;
        }

        /**
         * Add code with positional or relative arguments.
         *
         * <p>Relative arguments map 1:1 with the placeholders in the format string.
         *
         * <p>Positional arguments use an index after the placeholder to identify which argument index
         * to use. For example, for a literal to reference the 3rd argument: "$3L" (1 based index)
         *
         * <p>Mixing relative and positional arguments in a call to add is invalid and will result in an
         * error.
         */
        public Creator add(String format, Object... args)
        {
            boolean hasRelative = false;
            boolean hasIndexed = false;
            if (formatParts.size() != 0)
            {
                formatParts.add("\n");
            }

            int relativeParameterCount = 0;
            int[] indexedParameterCount = new int[args.length];

            for (int p = 0; p < format.length(); )
            {
                if (format.charAt(p) != '$')
                {
                    int nextP = format.indexOf('$', p + 1);
                    if (nextP == -1) nextP = format.length();
                    formatParts.add(format.substring(p, nextP));
                    p = nextP;
                    continue;
                }

                p++; // '$'.

                // Consume zero or more digits, leaving 'c' as the first non-digit char after the '$'.
                int indexStart = p;
                char c;
                do
                {
                    checkArgument(p < format.length(), "dangling format characters in '%s'", format);
                    c = format.charAt(p++);
                } while (c >= '0' && c <= '9');
                int indexEnd = p - 1;

                // If 'c' doesn't take an argument, we're done.
                if (isNoArgPlaceholder(c))
                {
                    checkArgument(
                            indexStart == indexEnd,
                            "$$, $>, $<, $[, $], $W, and $Z may not have an index"
                    );
                    formatParts.add("$" + c);
                    continue;
                }

                // Find either the indexed argument, or the relative argument. (0-based).
                int index;
                if (indexStart < indexEnd)
                {
                    index = Integer.parseInt(format.substring(indexStart, indexEnd)) - 1;
                    hasIndexed = true;
                    if (args.length > 0)
                    {
                        indexedParameterCount[index % args.length]++; // modulo is needed, checked below anyway
                    }
                } else
                {
                    index = relativeParameterCount;
                    hasRelative = true;
                    relativeParameterCount++;
                }

                checkArgument(
                        index >= 0 && index < args.length,
                        "index %d for '%s' not in range (received %s arguments)",
                        index + 1,
                        format.substring(indexStart - 1, indexEnd + 1),
                        args.length
                );
                checkArgument(!hasIndexed || !hasRelative, "cannot mix indexed and positional parameters");

                String param = format.substring(indexEnd);
                if (param.contains("$"))
                {
                    param = param.substring(0, param.indexOf("$"));
                }
                if(param.length() >= 2)
                {
                    if (param.charAt(1) == ':')
                    {
                        if (param.charAt(2) == 's')
                        {
                            if(format.length() > indexEnd + 2)
                            {
                                format = format.substring(0, indexEnd + 1) + format.substring(indexEnd + 3);
                            }
                            addArgument(format, c, args[index], true, false);
                        } else if (param.charAt(2) == 'S')
                        {
                            if(format.length() > indexEnd + 2)
                            {
                                format = format.substring(0, indexEnd + 1) + format.substring(indexEnd + 3);
                            }
                            addArgument(format, c, args[index], true, true);
                        } else {
                            addArgument(format, c, args[index]);
                        }
                    } else {
                        addArgument(format, c, args[index]);
                    }
                } else {
                    addArgument(format, c, args[index]);
                }
                formatParts.add("$" + c);
            }

            if (hasRelative)
            {
                checkArgument(relativeParameterCount >= args.length,
                        "unused arguments: expected %s, received %s", relativeParameterCount, args.length);
            }
            if (hasIndexed)
            {
                List<String> unused = new ArrayList<>();
                for (int i = 0; i < args.length; i++)
                {
                    if (indexedParameterCount[i] == 0)
                    {
                        unused.add("$" + (i + 1));
                    }
                }
                String s = unused.size() == 1 ? "" : "s";
                checkArgument(unused.isEmpty(), "unused argument%s: %s", s, unused.toArray());
            }
            return this;
        }

        private boolean isNoArgPlaceholder(char c)
        {
            return c == '$' || c == '>' || c == '<' || c == '[' || c == ']' || c == 'W' || c == 'Z';
        }

        private void addArgument(String format, char c, Object arg)
        {
            addArgument(format, c, arg, false, false);
        }
        private void addArgument(String format, char c, Object arg, boolean camelCase, boolean upper)
        {
            switch (c)
            {
                case 'N':
                    if (camelCase)
                    {
                        if (upper)
                        {
                            this.args.add(Util.toUpperCamel(argToName(arg)));
                        } else
                        {
                            this.args.add(Util.toLowerCamel(argToName(arg)));
                        }
                    } else
                    {
                        this.args.add(argToName(arg));
                    }
                    break;
                case 'L':
                    this.args.add(argToLiteral(arg));
                    break;
                case 'S':
                    if (camelCase)
                    {
                        if (upper)
                        {
                            this.args.add(Util.toUpperCamel(argToString(arg)));
                        } else {
                            this.args.add(Util.toLowerCamel(argToString(arg)));
                        }
                    } else
                    {
                        this.args.add(argToString(arg));
                    }
                    break;
                case 'C':
                    this.args.add(argToClass(arg, true));
                    break;
                case 'c':
                    this.args.add(argToClass(arg, false));
                    break;
                default:
                    throw new IllegalArgumentException(String.format(
                            "invalid format string: '%s'",
                            format
                    ));
            }
        }

        private String argToName(Object o)
        {
            if (o instanceof CharSequence) return o.toString();
            throw new IllegalArgumentException("expected name but was " + o);
        }

        private Object argToLiteral(Object o)
        {
            return o;
        }

        private String argToString(Object o)
        {
            return o != null ? String.valueOf(o) : null;
        }

        private String argToClass(Object o, boolean withPackage)
        {
            if (o instanceof Class<?>)
            {
                Class<?> clazz = (Class<?>) o;
                StringBuilder anonymousSuffix = new StringBuilder();
                while (true)
                {
                    assert clazz != null;
                    if (!clazz.isAnonymousClass()) break;
                    int lastDollar = clazz.getName().lastIndexOf('$');
                    anonymousSuffix.insert(0, clazz.getName().substring(lastDollar));
                    clazz = clazz.getEnclosingClass();
                }
                String name = clazz.getSimpleName() + anonymousSuffix;

                if (clazz.getEnclosingClass() == null)
                {
                    if (withPackage)
                    {
                        return clazz.getName() + ".class";
                    } else
                    {
                        int lastDot = clazz.getName().lastIndexOf(".");
                        if (lastDot != -1)
                        {
                            return clazz.getName().substring(lastDot + 1) + ".class";
                        } else
                        {
                            return clazz.getName() + ".class";
                        }
                    }
                }
                return clazz.getEnclosingClass().getCanonicalName() + ".class";
            }
            throw new IllegalArgumentException("expected class but was " + o);
        }

        public Creator add(StringCreator stringCreator)
        {
            formatParts.addAll(stringCreator.formatParts);
            args.addAll(stringCreator.args);
            return this;
        }

        public Creator clear()
        {
            formatParts.clear();
            args.clear();
            return this;
        }

        public StringCreator create()
        {
            return new StringCreator(this);
        }

        public String asString()
        {
            return new StringCreator(this).toString();
        }

    }

    @SuppressWarnings("UnusedReturnValue")
    private static final class CodeBlockJoiner
    {

        private final String delimiter;
        private final Creator creator;
        private boolean first = true;

        CodeBlockJoiner(String delimiter, Creator creator)
        {
            this.delimiter = delimiter;
            this.creator = creator;
        }

        CodeBlockJoiner add(StringCreator stringCreator)
        {
            if (!first)
            {
                creator.add(delimiter);
            }
            first = false;

            creator.add(stringCreator);
            return this;
        }

        CodeBlockJoiner merge(CodeBlockJoiner other)
        {
            StringCreator otherBlock = other.creator.create();
            if (!otherBlock.isEmpty())
            {
                add(otherBlock);
            }
            return this;
        }

        StringCreator join()
        {
            return creator.create();
        }

    }

}

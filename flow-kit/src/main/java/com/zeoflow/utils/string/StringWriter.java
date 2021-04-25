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

import java.io.IOException;
import java.util.regex.Pattern;

import static com.zeoflow.utils.string.Util.checkArgument;
import static com.zeoflow.utils.string.Util.checkNotNull;
import static com.zeoflow.utils.string.Util.checkState;
import static com.zeoflow.utils.string.Util.stringLiteralWithDoubleQuotes;

@SuppressWarnings({"UnusedReturnValue", "unused", "RedundantSuppression"})
final class StringWriter
{

    /**
     * Sentinel value that indicates that no user-provided package has been set.
     */
    private static final Pattern LINE_BREAKING_PATTERN = Pattern.compile(Pattern.quote("\\R"));

    private final String indent;
    private final LineWrapper out;
    /**
     * When emitting a statement, this is the line of the statement currently being written. The first
     * line of a statement is indented normally and subsequent wrapped lines are double-indented. This
     * is -1 when the currently-written line isn't part of a statement.
     */
    int statementLine = -1;
    private int indentLevel;
    private boolean trailingNewline;

    StringWriter(Appendable out)
    {
        this(out, "  ");
    }

    StringWriter(Appendable out,
                 String indent)
    {
        this.out = new LineWrapper(out, indent, 100);
        this.indent = checkNotNull(indent, "indent == null");
    }
    public StringWriter indent()
    {
        return indent(1);
    }
    public StringWriter indent(int levels)
    {
        indentLevel += levels;
        return this;
    }
    public StringWriter unindent()
    {
        return unindent(1);
    }
    public StringWriter unindent(int levels)
    {
        checkArgument(indentLevel - levels >= 0, "cannot unindent %s from %s", levels, indentLevel);
        indentLevel -= levels;
        return this;
    }
    public StringWriter emit(String s) throws IOException
    {
        return emitAndIndent(s);
    }
    public StringWriter emit(String format, Object... args) throws IOException
    {
        return emit(StringCreator.of(format, args));
    }
    public StringWriter emit(StringCreator stringCreator) throws IOException
    {
        return emit(stringCreator, false);
    }
    public StringWriter emit(StringCreator stringCreator, boolean ensureTrailingNewline) throws IOException
    {
        int a = 0;
        for (String part : stringCreator.formatParts)
        {
            switch (part)
            {
                case "$L":
                    emitLiteral(stringCreator.args.get(a++));
                    break;

                case "$c":
                case "$C":
                    emitClass((String) stringCreator.args.get(a++));
                    break;

                case "$N":
                    emitAndIndent((String) stringCreator.args.get(a++));
                    break;

                case "$S":
                    String string = (String) stringCreator.args.get(a++);
                    // Emit null as a literal null: no quotes.
                    emitAndIndent(string != null
                            ? stringLiteralWithDoubleQuotes(string, indent)
                            : "null");
                    break;

                case "$$":
                    emitAndIndent("$");
                    break;

                case "$>":
                    indent();
                    break;

                case "$<":
                    unindent();
                    break;

                case "$[":
                    checkState(statementLine == -1, "statement enter $[ followed by statement enter $[");
                    statementLine = 0;
                    break;

                case "$]":
                    checkState(statementLine != -1, "statement exit $] has no matching statement enter $[");
                    if (statementLine > 0)
                    {
                        unindent(2); // End a multi-line statement. Decrease the indentation level.
                    }
                    statementLine = -1;
                    break;

                case "$W":
                    out.wrappingSpace(indentLevel + 2);
                    break;

                case "$Z":
                    out.zeroWidthSpace(indentLevel + 2);
                    break;

                default:
                    // handle deferred type
                    emitAndIndent(part);
                    break;
            }
        }
        if (ensureTrailingNewline && out.lastChar() != '\n')
        {
            emit("\n");
        }
        return this;
    }
    public StringWriter emitWrappingSpace() throws IOException
    {
        out.wrappingSpace(indentLevel + 2);
        return this;
    }

    private void emitLiteral(Object o) throws IOException
    {
        if (o instanceof StringCreator)
        {
            StringCreator stringCreator = (StringCreator) o;
            emit(stringCreator);
        } else
        {
            emitAndIndent(String.valueOf(o));
        }
    }

    /**
     * Emits {@code s} with indentation as required. It's important that all code that writes to
     * {@link #out} does it through here, since we emit indentation lazily in order to avoid
     * unnecessary trailing whitespace.
     */
    StringWriter emitAndIndent(String s) throws IOException
    {
        boolean first = true;
        for (String line : LINE_BREAKING_PATTERN.split(s, -1))
        {
            // Emit a newline character. Make sure blank lines in Javadoc & comments look good.
            if (!first)
            {
                out.append("\n");
                trailingNewline = true;
                if (statementLine != -1)
                {
                    if (statementLine == 0)
                    {
                        indent(2); // Begin multiple-line statement. Increase the indentation level.
                    }
                    statementLine++;
                }
            }

            first = false;
            if (line.isEmpty()) continue; // Don't indent empty lines.

            // Emit indentation and comment prefix if necessary.
            if (trailingNewline)
            {
                emitIndentation();
            }

            out.append(line);
            trailingNewline = false;
        }
        return this;
    }
    /**
     * Emits {@code s} with indentation as required. It's important that all code that writes to
     * {@link #out} does it through here, since we emit indentation lazily in order to avoid
     * unnecessary trailing whitespace.
     */
    StringWriter emitClass(String s) throws IOException
    {
        boolean first = true;
        for (String line : LINE_BREAKING_PATTERN.split(s, -1))
        {
            // Emit a newline character. Make sure blank lines in Javadoc & comments look good.
            if (!first)
            {
                out.append("\n");
                trailingNewline = true;
                if (statementLine != -1)
                {
                    if (statementLine == 0)
                    {
                        indent(2); // Begin multiple-line statement. Increase the indentation level.
                    }
                    statementLine++;
                }
            }

            first = false;
            if (line.isEmpty()) continue; // Don't indent empty lines.

            // Emit indentation and comment prefix if necessary.
            if (trailingNewline)
            {
                emitIndentation();
            }

            out.append(line);
            trailingNewline = false;
        }
        return this;
    }

    private void emitIndentation() throws IOException
    {
        for (int j = 0; j < indentLevel; j++)
        {
            out.append(indent);
        }
    }

}

package com.zeoflow.storage.memo;

import android.content.Context;

import com.zeoflow.zson.Zson;

public class MemoBuilder {

  /**
   * NEVER ever change STORAGE_TAG_DO_NOT_CHANGE and TAG_INFO.
   * It will break backward compatibility in terms of keeping previous data
   */
  private static final String STORAGE_TAG_DO_NOT_CHANGE = "Hawk2";

  private Context context;
  private Storage cryptoStorage;
  private Converter converter;
  private Parser parser;
  private Encryption encryption;
  private Serializer serializer;
  private LogInterceptor logInterceptor;

  public MemoBuilder(Context context) {
    MemoUtils.checkNull("Context", context);

    this.context = context.getApplicationContext();
  }

  public MemoBuilder setStorage(Storage storage) {
    this.cryptoStorage = storage;
    return this;
  }

  public MemoBuilder setParser(Parser parser) {
    this.parser = parser;
    return this;
  }

  public MemoBuilder setSerializer(Serializer serializer) {
    this.serializer = serializer;
    return this;
  }

  public MemoBuilder setLogInterceptor(LogInterceptor logInterceptor) {
    this.logInterceptor = logInterceptor;
    return this;
  }

  public MemoBuilder setConverter(Converter converter) {
    this.converter = converter;
    return this;
  }

  public MemoBuilder setEncryption(Encryption encryption) {
    this.encryption = encryption;
    return this;
  }

  LogInterceptor getLogInterceptor() {
    if (logInterceptor == null) {
      logInterceptor = new LogInterceptor() {
        @Override
        public void onLog(String message) {
          //empty implementation
        }
      };
    }
    return logInterceptor;
  }

  Storage getStorage() {
    if (cryptoStorage == null) {
      cryptoStorage = new SharedPreferencesStorage(context, STORAGE_TAG_DO_NOT_CHANGE);
    }
    return cryptoStorage;
  }

  Converter getConverter() {
    if (converter == null) {
      converter = new MemoConverter(getParser());
    }
    return converter;
  }

  Parser getParser() {
    if (parser == null) {
      parser = new ZsonParser(new Zson());
    }
    return parser;
  }

  Encryption getEncryption() {
    if (encryption == null) {
      encryption = new ConcealEncryption(context);
      if (!encryption.init()) {
        encryption = new NoEncryption();
      }
    }
    return encryption;
  }

  Serializer getSerializer() {
    if (serializer == null) {
      serializer = new MemoSerializer(getLogInterceptor());
    }
    return serializer;
  }

  public void build() {
    Memo.build(this);
  }
}

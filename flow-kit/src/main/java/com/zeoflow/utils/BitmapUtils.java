// Copyright 2021 ZeoFlow SRL
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.zeoflow.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;

import com.zeoflow.initializer.ZeoFlowApp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import static com.zeoflow.initializer.ZeoFlowApp.getContext;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class BitmapUtils
{
    public static int[] getImageWidthHeight(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = ZeoFlowApp.getContext().getContentResolver()
                    .openFileDescriptor(uri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                return new int[]{image.getWidth(), image.getHeight()};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new int[]{0, 0};
    }

    public static Bitmap getBitmapFromUri(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = ZeoFlowApp.getContext().getContentResolver()
                    .openFileDescriptor(uri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                return image;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int[] getImageWidthHeight(String path) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[]{0, 0};
    }


    public static String DEFAULT_FILE_NAME = "file_name";
    public static File getPickerFileDirectory() {
        File file = new File(ZeoFlowApp.getContext().getExternalFilesDir(null), DEFAULT_FILE_NAME);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return file;
    }

    public static File getDCIMDirectory() {
        File dcim = getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM);
        assert dcim != null;
        if (!dcim.exists()) {
            dcim = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        return dcim;
    }
    public static String saveBitmapToFile(Bitmap bitmap,
                                          String fileName,
                                          Bitmap.CompressFormat compressFormat) {

        File file = getPickerFileDirectory();
        file = new File(file, fileName + "." + compressFormat.toString().toLowerCase());
        try {
            FileOutputStream b = new FileOutputStream(file);
            bitmap.compress(compressFormat, 90, b);
            b.flush();
            b.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
            return "Exception:" + e.getMessage();
        }
    }

    public static Uri saveBitmapToDCIM(Bitmap bitmap,
                                       String fileName,
                                       Bitmap.CompressFormat compressFormat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/" + compressFormat.toString());
        contentValues.put(MediaStore.Files.FileColumns.WIDTH, bitmap.getWidth());
        contentValues.put(MediaStore.Files.FileColumns.HEIGHT, bitmap.getHeight());
        String suffix = "." + compressFormat.toString().toLowerCase();
        String path = getDCIMDirectory().getAbsolutePath() + File.separator + fileName + suffix;
        try {
            contentValues.put(MediaStore.Images.Media.DATA, path);
        } catch (Exception ignored) {

        }
        Uri uri = ZeoFlowApp.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (uri != null) {
            try {
                OutputStream outputStream = ZeoFlowApp.getContext().getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    bitmap.compress(compressFormat, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    return uri;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return uri;
            }
        }

        return uri;
    }
    public static String getPathFromUri(Uri uri) {
        String path = "";
        String DATA = Build.VERSION.SDK_INT < 29 ?
                MediaStore.Images.ImageColumns.DATA
                : MediaStore.Images.ImageColumns.RELATIVE_PATH;
        Cursor cursor = ZeoFlowApp.getContext().getContentResolver().query(uri, new String[]{DATA},
                null, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DATA);
                if (index > -1)
                    path = cursor.getString(index);
            }
            cursor.close();
        }
        return path;
    }

    private static boolean copyFile(String sourceFilePath, final Uri insertUri) {
        if (insertUri == null) {
            return false;
        }
        ContentResolver resolver = ZeoFlowApp.getContext().getContentResolver();
        InputStream is = null;
        OutputStream os = null;
        try {
            os = resolver.openOutputStream(insertUri);
            if (os == null) {
                return false;
            }
            File sourceFile = new File(sourceFilePath);
            if (sourceFile.exists()) {
                is = new FileInputStream(sourceFile);
                return copyFileWithStream(os, is);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    private static boolean copyFileWithStream(OutputStream os, InputStream is) {
        if (os == null || is == null) {
            return false;
        }
        int read;
        while (true) {
            try {
                byte[] buffer = new byte[1444];
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                    os.flush();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getViewBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        return view.getDrawingCache(true);
    }


    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    public static long getLocalVideoDuration(String videoPath) {
        int duration;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            duration = Integer.parseInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return duration;
    }
    public static void refreshGalleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        ZeoFlowApp.getContext().sendBroadcast(mediaScanIntent);
    }

    public static String getMimeTypeFromUri(Uri uri) {
        ContentResolver resolver = ZeoFlowApp.getContext().getContentResolver();
        return resolver.getType(uri);
    }

    public static String getMimeTypeFromPath(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(new File(path).getName());
    }


    public static Uri getImageContentUri(String path) {
        Cursor cursor = ZeoFlowApp.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            return null;
        }
    }

    public static Uri getVideoContentUri(String path) {
        Cursor cursor = ZeoFlowApp.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            return null;
        }
    }
}

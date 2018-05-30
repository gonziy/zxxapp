package com.zxxapp.mall.maintenance.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.zxxapp.mall.maintenance.R;

import java.io.File;

/**
 * Created by yinyy on 2017/10/5.
 */

public class FileUtil{
        public static boolean isLocal(String url) {
            if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
                return true;
            }
            return false;
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is {@link }.
         * @author paulburke
         */
        public static boolean isLocalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         * @author paulburke
         */
        public static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         * @author paulburke
         */
        public static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         * @author paulburke
         */
        public static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        public static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context The context.
         * @param uri The Uri to query.
         * @param selection (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         * @author paulburke
         */
        public static String getDataColumn(Context context, Uri uri, String selection,
                                           String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    DatabaseUtils.dumpCursor(cursor);

                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        /**
         * Get a file path from a Uri. This will get the the path for Storage Access
         * Framework Documents, as well as the _data field for the MediaStore and
         * other file-based ContentProviders.<br>
         * <br>
         * Callers should check whether the path is local before assuming it
         * represents a local file.
         *
         * @param context The context.
         * @param uri The Uri to query.
         * @see #isLocal(String)
         * @see #getFile(Context, Uri)
         * @author paulburke
         */
        public static String getPath(final Context context, final Uri uri) {

            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // LocalStorageProvider
                if (isLocalStorageDocument(uri)) {
                    // The path is the id
                    return DocumentsContract.getDocumentId(uri);
                }
                // ExternalStorageProvider
                else if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }

        /**
         * Convert Uri into File, if possible.
         *
         * @return file A local file that the Uri was pointing to, or null if the
         *         Uri is unsupported or pointed to a remote resource.
         * @see #getPath(Context, Uri)
         * @author paulburke
         */
        public static File getFile(Context context, Uri uri) {
            if (uri != null) {
                String path = getPath(context, uri);
                if (path != null && isLocal(path)) {
                    return new File(path);
                }
            }
            return null;
        }

    public static boolean isBitmap(String filename){
        String pattern = ".png.jpg.jpeg.bmp.gif";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isAudio(String filename){
        String pattern = ".mp3";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isVideo(String filename){
        String pattern = ".mp4.avi.mpg";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isZip(String filename){
        String pattern = ".zip.rar";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isDoc(String filename){
        String pattern = ".doc.docx";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isXls(String filename){
        String pattern = ".xls.xlsx";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isPpt(String filename){
        String pattern = ".ppt.pptx";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static boolean isPdf(String filename){
        String pattern = ".pdf";
        String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();

        return pattern.indexOf(ext)>=0;
    }

    public static int getFileIcon(String filename){
        if(isAudio(filename)){
            return R.drawable.ic_file_type_audio;
        }else if(isDoc(filename)){
            return R.drawable.ic_file_type_doc;
        }else if(isZip(filename)){
            return R.drawable.ic_file_type_zip;
        }else if(isXls(filename)){
            return R.drawable.ic_file_type_xls;
        }else if(isVideo(filename)){
            return R.drawable.ic_file_type_video;
        }else if(isPpt(filename)){
            return R.drawable.ic_file_type_ppt;
        }else if(isPdf(filename)){
            return R.drawable.ic_file_type_pdf;
        }else {
            return R.drawable.ic_file_type_unknown;
        }

    }
//
//    public static String getFilePath(Context context, Uri uri){
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA, MediaStore.Audio.Media.DATA, MediaStore.Video.Media.DATA};
//            cursor = context.getContentResolver().query(uri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//
//            return cursor.getString(column_index);
//        } catch (Exception e){
//            return null;
//        }finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
//
//    public static int tryGetFileIconResourceId(String filename, int defaultImageRes) {
//        filename = filename.toLowerCase();
//
//        if (filename.endsWith(".zip")) {
//            return R.drawable.ic_file_type_zip;
//        } else if (filename.endsWith("ppt") || filename.endsWith("pptx")) {
//            return R.drawable.ic_file_type_ppt;
//        } else if (filename.endsWith("pdf")) {
//            return R.drawable.ic_file_type_pdf;
//        } else if (filename.endsWith("xls") || filename.endsWith("xlsx")) {
//            return R.drawable.ic_file_type_xls;
//        } else if (filename.endsWith("doc") || filename.endsWith("docx")) {
//            return R.drawable.ic_file_type_doc;
//        } else if (filename.endsWith("rar")) {
//            return R.drawable.ic_file_type_rar;
//        } else if (filename.endsWith("mp4")) {
//            return R.drawable.ic_file_type_video;
//        } else if (filename.endsWith("mp3")) {
//            return R.drawable.ic_file_type_mp3;
//        } else {
//            return defaultImageRes;
//        }
//    }
}

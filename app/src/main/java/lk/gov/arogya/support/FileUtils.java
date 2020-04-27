package lk.gov.arogya.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FileUtils {

    private final static int READ_BLOCK_SIZE = 8192;
    public static final int MULTIPLE_PERMISSIONS = 100;

    public static void saveArrayList(Context context, String key, ArrayList<String> list) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public static ArrayList<String> getArrayList(String key, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String readFromStorage(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        String content = "";
        if (fileName == null) {
            return null;
        }
        try {
            if (file.exists()) {
                FileInputStream filein = new FileInputStream(file);  // 2nd line
                InputStreamReader inputReader = new InputStreamReader(filein);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = inputReader.read(inputBuffer)) > 0) {
                    // char to string conversion
                    content = String.copyValueOf(inputBuffer, 0, charRead);
                }
                inputReader.close();
                return content;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToStorage(String filePath, String fileName, String content) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileout = new FileOutputStream(file + "/" + fileName, true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(content);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHash(String string) {
        String hashedString = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(string.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedString = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedString;
    }
}

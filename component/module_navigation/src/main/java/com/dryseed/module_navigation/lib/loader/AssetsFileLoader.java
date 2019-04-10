package com.dryseed.module_navigation.lib.loader;

import android.content.res.AssetManager;
import com.dryseed.module_navigation.lib.entity.TopMenuTabEntity;
import com.easy.moduler.lib.okbus.BaseAppModuleApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author caiminming
 */
public class AssetsFileLoader<T> extends AbsLoader<T> {
    @Override
    public ArrayList<TopMenuTabEntity> parseData() {
        if (!(mOriginData instanceof String)) {
            return new ArrayList<>();
        }
        String fileName = (String) mOriginData;
        return loadAssetsFile(fileName);
    }

    private ArrayList<TopMenuTabEntity> loadAssetsFile(String fileName) {
        ArrayList<TopMenuTabEntity> tabEntities = new ArrayList<>();
        AssetManager assetManager = BaseAppModuleApp.getInstance().getAssets();
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = assetManager.open(fileName);
            br = new BufferedReader(new InputStreamReader(is));

            Type type = new TypeToken<ArrayList<TopMenuTabEntity>>() {
            }.getType();
            tabEntities = new Gson().fromJson(br, type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tabEntities;
    }
}

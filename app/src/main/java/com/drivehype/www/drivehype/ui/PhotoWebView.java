package com.drivehype.www.drivehype.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.drivehype.www.drivehype.R;

/**
 * Copyright 2015 Elliott Edward Goldman,
 * <p/>
 * Licensed under the Apache License, Verstion 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author Elliott Edward Goldman elliott.goldman@asu.edu
 *         Computer Science, IAFSE, Arizona State University
 * @version February 05, 2015
 */

public class PhotoWebView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_channels);

        //Uri uri = Uri.parse("http://www.example.com");
        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //startActivity(intent);

        WebView photoWebView = (WebView) findViewById(R.id.webview);

        //photoWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        photoWebView.getSettings().setJavaScriptEnabled(true);
        photoWebView.loadUrl("http://www.drivehype.com/albums.html");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

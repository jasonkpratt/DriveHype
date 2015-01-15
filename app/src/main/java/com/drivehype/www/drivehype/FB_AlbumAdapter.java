package com.drivehype.www.drivehype;

/**
 * Created by JasonPratt on 1/13/15.
 */


        import  android.app.Activity;
        import android.os.AsyncTask;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.content.Context;
        import android.content.ClipData.Item;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.net.URL;
        import android.graphics.BitmapFactory;
        import android.graphics.Bitmap;
        import android.util.Log;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.HttpStatus;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import java.io.InputStream;


public class FB_AlbumAdapter extends ArrayAdapter<String>  {

    private final Context context;
    private final String[] albumTitles;
    private final String[] albumImages;
    int layoutResourceId;
    ImageView imageView;


        public FB_AlbumAdapter(Context context, int layoutResourceId, String [] titles, String [] images) {
            super(context, R.layout.fragment_fb, titles);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.albumTitles=titles;
            this.albumImages=images;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView=inflater.inflate(R.layout.row_grid, null,true);


            TextView txtTitle = (TextView) rowView.findViewById(R.id.item_text);
            imageView = (ImageView) rowView.findViewById(R.id.item_image);
            //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
            Log.d("position", "adapter count"+position);
            Log.d("position", "albumTitles is null"+albumTitles[position].isEmpty());

            txtTitle.setText(albumTitles[position]);

            new ImageDownloader().execute(albumImages[position]);


/*
            try{
                URL img_value = null;
                img_value = new URL(albumImages[position]);
               // Bitmap albumPic = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
               // int id = getResources().getIdentifier("yourpackagename:drawable/" + StringGenerated, null, null);
                int id=R.drawable.home_icon;
                imageView.setImageResource(id);

            }
            catch (IOException e){} */

            return rowView;


        }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... param) {
            // TODO Auto-generated method stub
            return downloadBitmap(param[0]);
        }

        @Override
        protected void onPreExecute() {
            Log.i("Async-Example", "onPreExecute Called");
            // simpleWaitDialog = ProgressDialog.show(ImageDownladerActivity.this,
            //   "Wait", "Downloading Image");

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("Async-Example", "onPostExecute Called");
            imageView.setImageBitmap(result);
            //simpleWaitDialog.dismiss();

        }

        private Bitmap downloadBitmap(String url) {
            // initilize the default HTTP client object
            final DefaultHttpClient client = new DefaultHttpClient();

            //forming a HttoGet request
            final HttpGet getRequest = new HttpGet(url);
            try {

                HttpResponse response = client.execute(getRequest);

                //check 200 OK for success
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("ImageDownloader", "Error " + statusCode +
                            " while retrieving bitmap from " + url);
                    return null;

                }

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        // getting contents from the stream
                        inputStream = entity.getContent();

                        // decoding stream data back into image Bitmap that android understands
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        return bitmap;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                getRequest.abort();
                Log.e("ImageDownloader", "Something went wrong while" +
                        " retrieving bitmap from " + url + e.toString());
            }

            return null;
        }
    }

            /*View row = convertView;
            RecordHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new RecordHolder();
                holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
                holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }


            holder.txtTitle.setText(albumTitles[position]);

            try{
            URL img_value = null;
            img_value = new URL(albumImages[position]);
            Bitmap albumPic = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
            holder.imageItem.setImageBitmap(albumPic);

            }
            catch (IOException e){}

            return row;

        }

        static class RecordHolder {
            TextView txtTitle;
            ImageView imageItem;

        }*/
    }
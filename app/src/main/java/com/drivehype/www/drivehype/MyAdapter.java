package com.drivehype.www.drivehype;

/**
 * Created by JasonPratt on 1/6/15.
 */
import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.content.Context;

public class MyAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] itemname;
    private final Integer[] imgid;

    public MyAdapter(Context context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.nav_drawer_layout, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        //LayoutInflater inflater=context.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView=inflater.inflate(R.layout.nav_drawer_layout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
       // extratxt.setText("Description "+itemname[position]);
        return rowView;

    };
}
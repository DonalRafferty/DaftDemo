package com.donalrafferty.daftdemo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.donalrafferty.daftdemo.activities.DetailActivity;
import com.donalrafferty.daftdemo.objects.Ad;
import com.donalrafferty.daftdemo.utils.DaftConstants;
import com.donalrafferty.daftdemo.utils.DaftDetailUIHelper;


public class DetailActivityTest extends ActivityInstrumentationTestCase2<DetailActivity> {

    private DetailActivity mDetailActivity;
    private DaftDetailUIHelper daftDetailUIHelper;
    private NetworkImageView propertyAdvertImage;
    private TextView mTvPrice;
    private Button mBtPhone;
    private ImageView berImage;
    Ad advert;
    //...

    public DetailActivityTest() {
        super(DetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        advert = new Ad();
        advert.setLarge_thumbnail_url("http://www.daft.ie");
        advert.setPrice(700000);
        advert.setPhone1("0863432434");
        advert.setBer_rating("A1");

        Intent i = new Intent(getInstrumentation().getTargetContext(), DetailActivity.class);
        i.putExtra(DaftConstants.PARCELABLE_AD_KEY, advert);
        setActivityIntent(i);
        mDetailActivity = getActivity();

        daftDetailUIHelper = new DaftDetailUIHelper(mDetailActivity.getApplicationContext());
        daftDetailUIHelper.setupViews(mDetailActivity);

        propertyAdvertImage =
                (NetworkImageView) mDetailActivity
                        .findViewById(R.id.image_advert);
        mTvPrice =
                (TextView) mDetailActivity
                        .findViewById(R.id.text_price_detail);
        mBtPhone =
                (Button) mDetailActivity
                        .findViewById(R.id.button_phone_detail);
        berImage =
                (ImageView) mDetailActivity
                        .findViewById(R.id.image_ber_detail);
        //...


    }

    public void testPreconditions() {
        assertNotNull("DetailActivity is null", mDetailActivity);
        assertNotNull("daftDetailUIHelper is null", daftDetailUIHelper);
        assertNotNull("propertyAdvertImage is null", propertyAdvertImage);
        assertNotNull("mTvPrice is null", mTvPrice);
        assertNotNull("mBtPhone is null", mBtPhone);
        assertNotNull("berImage is null", berImage);
        assertNotNull("advert is null", advert);
        //...
    }

    public void testUIPopulation(){
        mDetailActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                daftDetailUIHelper.populateViews(advert);
            }
        });
        assert propertyAdvertImage.getImageURL().equals("http://www.daft.ie");
        assert mTvPrice.getText().toString().equals("€700,000");
        assert mBtPhone.getText().toString().equals("Phone");
        //...

    }


}

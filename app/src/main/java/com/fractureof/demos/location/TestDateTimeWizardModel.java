package com.fractureof.demos.location;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.fractureof.demos.location.wizard.model.AbstractWizardModel;
import com.fractureof.demos.location.wizard.model.MyLocationPage;
import com.fractureof.demos.location.wizard.model.PageList;
import com.fractureof.demos.location.wizard.model.DatesTimePage;

/**
 * Created by tyler on 19/06/2016.
 */
public class TestDateTimeWizardModel extends AbstractWizardModel {

    static class Consts {
        public static String DATESTIME_PAGE_TITLE = "Date's Time";
        public static String MY_LOCATION_PAGE_TITLE = "Your Location";
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new DatesTimePage(this,
                        Consts.DATESTIME_PAGE_TITLE,
                        SplashActivity.v1DatePlan
                ).setRequired(true)
                ,
                new MyLocationPage(this,
                        Consts.MY_LOCATION_PAGE_TITLE,
                        SplashActivity.v1DatePlan
                ).setRequired(true)

        );
    }
}

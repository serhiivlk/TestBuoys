package com.thermsx.localbuoys.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoonPhasesInfo {

    @SerializedName("Phases")
    @Expose
    private Phases mPhases;
    @SerializedName("SunInfos")
    @Expose
    private SunInfo mSunInfo;
    @SerializedName("MoonInfos")
    @Expose
    private List<MoonInfo> mMoonInfo;

    public Phases getPhases() {
        return mPhases;
    }

    public SunInfo getSunInfo() {
        return mSunInfo;
    }

    public List<MoonInfo> getMoonInfo() {
        return mMoonInfo;
    }

    public class Phases {

        @SerializedName("NewMoon")
        @Expose
        private String newMoon;
        @SerializedName("FirstQuarter")
        @Expose
        private String firstQuarter;
        @SerializedName("FullMoon")
        @Expose
        private String fullMoon;
        @SerializedName("LastQuarter")
        @Expose
        private String lastQuarter;

        public String getNewMoon() {
            return newMoon;
        }

        public String getFirstQuarter() {
            return firstQuarter;
        }

        public String getFullMoon() {
            return fullMoon;
        }

        public String getLastQuarter() {
            return lastQuarter;
        }
    }

    public class SunInfo {

        @SerializedName("Sunrise")
        @Expose
        private String mSunrise;
        @SerializedName("Sunset")
        @Expose
        private String mSunset;

        public String getSunset() {
            return mSunset;
        }

        public String getSunrise() {
            return mSunrise;
        }
    }

    public class MoonInfo {
        @SerializedName("Name")
        @Expose
        private String mName;
        @SerializedName("Time")
        @Expose
        private String mTime;
        @SerializedName("Note")
        @Expose
        private String mNote;

        public String getName() {
            return mName;
        }

        public String getTime() {
            return mTime;
        }

        public String getNote() {
            return mNote;
        }
    }
}

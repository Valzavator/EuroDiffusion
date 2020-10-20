package com.gmail.maxsmv1998.eurodiffusion.data;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class CountryData implements Serializable {
    private static final long serialVersionUID = -1690432463196581631L;

    private final String name;
    private final int xl;
    private final int yl;
    private final int xh;
    private final int yh;

    public CountryData(String name, int xl, int yl, int xh, int yh) {
        this.name = name;
        this.xl = xl;
        this.yl = yl;
        this.xh = xh;
        this.yh = yh;
    }

    public static class Builder {
        private String name;
        private int xl;
        private int yl;
        private int xh;
        private int yh;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setXL(int xl) {
            this.xl = xl;
            return this;
        }

        public Builder setYL(int yl) {
            this.yl = yl;
            return this;
        }

        public Builder setXH(int xh) {
            this.xh = xh;
            return this;
        }

        public Builder setYH(int yh) {
            this.yh = yh;
            return this;
        }

        public CountryData build() {
            return new CountryData(name, xl, yl, xh, yh);
        }
    }

    public String getName() {
        return name;
    }

    public int getXL() {
        return xl;
    }

    public int getYL() {
        return yl;
    }

    public int getXH() {
        return xh;
    }

    public int getYH() {
        return yh;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CountryData.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("xl=" + xl)
                .add("yl=" + yl)
                .add("xh=" + xh)
                .add("yh=" + yh)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryData that = (CountryData) o;
        return xl == that.xl &&
                yl == that.yl &&
                xh == that.xh &&
                yh == that.yh &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, xl, yl, xh, yh);
    }
}

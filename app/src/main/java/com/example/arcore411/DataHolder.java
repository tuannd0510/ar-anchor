package com.example.arcore411;

public class DataHolder {
    // Camera pose
    private double Ctx, Cty, Ctz, Cqx, Cqy, Cqz, Cqw;
    // Hit pose
    private double Htx, Hty, Htz, Hqx, Hqy, Hqz, Hqw;

    /**
     * Getter
     */
    // getter Camera pose
    public double getCtx() {return Ctx;}
    public double getCty() {return Cty;}
    public double getCtz() {return Ctz;}
    public double getCqx() {return Cqx;}
    public double getCqy() {return Cqy;}
    public double getCqz() {return Cqz;}
    public double getCqw() {return Cqw;}
    // getter Hit pose
    public double getHtx() {return Htx;}
    public double getHty() {return Hty;}
    public double getHtz() {return Htz;}
    public double getHqx() {return Hqx;}
    public double getHqy() {return Hqy;}
    public double getHqz() {return Hqz;}
    public double getHqw() {return Hqw;}

    /**
     * Setter
     */
    // setter Camera pose
    public void setCtx(double ctx) {Ctx = ctx;}
    public void setCty(double cty) {Cty = cty;}
    public void setCtz(double ctz) {Ctz = ctz;}
    public void setCqx(double cqx) {Cqx = cqx;}
    public void setCqy(double cqy) {Cqy = cqy;}
    public void setCqz(double cqz) {Cqz = cqz;}
    public void setCqw(double cqw) {Cqw = cqw;}
    // setter Hit pose
    public void setHtx(double htx) {Htx = htx;}
    public void setHty(double hty) {Hty = hty;}
    public void setHtz(double htz) {Htz = htz;}
    public void setHqx(double hqx) {Hqx = hqx;}
    public void setHqy(double hqy) {Hqy = hqy;}
    public void setHqz(double hqz) {Hqz = hqz;}
    public void setHqw(double hqw) {Hqw = hqw;}


    public static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
}

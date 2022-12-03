package yakofinance;

import java.util.Date;

public class Data {

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getCurrency() {
        return currency;
    }

    public void setCurrency(Double currency) {
        this.currency = currency;
    }

    public Double getHight() {
        return hight;
    }

    public void setHight(Double hight) {
        this.hight = hight;
    }

    @Override
    public String toString() {
        return "Data{" +
                "date=" + date +
                ", currency=" + currency +
                ", hight=" + hight +
                '}';
    }

    private Date date;
    private Double currency;
    private Double hight;

}

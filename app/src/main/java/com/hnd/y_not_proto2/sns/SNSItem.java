package com.hnd.y_not_proto2.sns;

/**
 * Created by Administrator on 2016-03-22.
 */
public class SNSItem {

    public String nametext;
    public String contenttext;
    public String cntgood;
    public String cntcomment;
    public int img;

    public SNSItem(String text, String text2, String text3, String text4, int img){
        this.contenttext = text;
        this.nametext = text2;
        this.cntgood = text3;//나중에 서버에서 받아오기
        this.cntcomment = text4; // 나중에 서버에서 받아오기
        this.img = img;

    }
}

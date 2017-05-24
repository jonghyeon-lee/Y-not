package com.hnd.y_not_proto2.chat;

import android.content.Context;
import android.text.Spanned;

/**
 * Created by Administrator on 2016-03-18.
 */
public class AACcMessage {

    private boolean who;
    private Spanned symbol;
    private String text;
    //private View.OnClickListener ttsRead;

    public AACcMessage(boolean who, Spanned symbol, String text, Context context) {
        this.who=who;
        this.symbol=symbol;
        this.text=text;

        final String input = text;

        final Context tmp = context;

    }

    public boolean getWho() {    return who;}
    public Spanned getSymbol()  {   return symbol;}
    public String getText()    {   return text;}
    //public View.OnClickListener getTtsRead()  {   return ttsRead;}
}

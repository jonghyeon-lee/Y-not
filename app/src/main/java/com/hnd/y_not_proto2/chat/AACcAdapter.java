package com.hnd.y_not_proto2.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hnd.y_not_proto2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2016-03-18.
 */
public class AACcAdapter extends ArrayAdapter<AACcMessage> {
    private static final String TAG = "Main";
    private TextView mtv_AACc;
    private ImageView mbt_TTSread;
    private List<AACcMessage> ml_AACcMessage = new ArrayList<AACcMessage>();
    private LinearLayout m_SingleMessageContainer;
    private Context ct_Parent;
    //TTS
    TextToSpeech m_TTSobj;
    boolean m_isTTSInit;
    boolean m_isTTSSupport;
    private ArrayList<Locale> mAl_Languages;

    public AACcAdapter(Context context, int textViewResourceId, TextToSpeech tts, boolean isTTSInit,  ArrayList<Locale> langs)
    {
        super(context,textViewResourceId);
        ct_Parent = context;
        m_TTSobj = tts;
        m_isTTSInit=isTTSInit;
        mAl_Languages = langs;

        /*int available = m_TTSobj.isLanguageAvailable();    //해당 언어가 지원하는 언어인지 검사

        if (available < 0) {                    //지원하지 않으면 메시지 출력
            Toast.makeText(ct_Parent, R.string.msg_not_support_lang, Toast.LENGTH_SHORT).show();
            m_isTTSSupport = false;
        } else m_isTTSSupport = true;                //지원하는 언어이면 플래그 변경*/
    }

    @Override
    public void add(AACcMessage item) {
        ml_AACcMessage.add(item);
        super.add(item);
    }
    public int getCount()                       {return this.ml_AACcMessage.size();}
    public AACcMessage getItem (int index)      {return this.ml_AACcMessage.get(index);}
    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cv = convertView;
        if(cv == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cv = inflater.inflate(R.layout.chtr_chatlist_item, parent, false);
        }
        m_SingleMessageContainer = (LinearLayout) cv.findViewById(R.id.singleMessageContainer);
        mtv_AACc = (TextView) cv.findViewById(R.id.item_text_aac);

        AACcMessage AACcItem = getItem(position);





        mbt_TTSread = (ImageView) cv.findViewById(R.id.item_button_tts);
        mbt_TTSread.setBackgroundResource(R.drawable.tts);
        final String input = AACcItem.getText();
        mbt_TTSread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale systemLocale = ct_Parent.getResources().getConfiguration().locale;
                //String strLanguage = systemLocale.getLanguage();
                //Toast.makeText(ct_Parent, input, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "Current input content : " + input);

                int available = m_TTSobj.isLanguageAvailable(systemLocale);    //해당 언어가 지원하는 언어인지 검사

                if (available < 0) {                    //지원하지 않으면 메시지 출력
                    Toast.makeText(ct_Parent, R.string.msg_not_support_lang, Toast.LENGTH_SHORT).show();
                    m_isTTSSupport = false;
                } else m_isTTSSupport = true;

                if (!m_isTTSInit)                //초기화 안되었으면 메시지 출력
                    Toast.makeText(ct_Parent, R.string.msg_fail_init, Toast.LENGTH_SHORT).show();
                else if (!m_isTTSSupport)    //지원하지 않는 언어이면 메시지 출력
                    Toast.makeText(ct_Parent, R.string.msg_not_support_lang, Toast.LENGTH_SHORT).show();
                else {
                    if (TextUtils.isEmpty(input))            //빈 텍스트면 메시지 출력
                        Toast.makeText(ct_Parent, R.string.msg_success_init, Toast.LENGTH_SHORT).show();
                    else {
                        //Toast.makeText(ct_Parent, "PASS", Toast.LENGTH_SHORT).show();
                        //String strLanguage = systemLocale.getLanguage();
                        //Locale lang = mLanguages.get(mLanguage.getSelectedItemPosition()-1);	//선택한 언어
                        Log.e("TAG", "SystemLocale : " + systemLocale + ", ToString() : " + systemLocale.getLanguage() + ", Locale.KOREA : " + Locale.KOREA);
                        m_TTSobj.setLanguage(systemLocale);                                    //언어 설정.
                        //mTTS.setPitch(mPitch.getProgress()/10.0f);				//pitch 설정.
                        //mTTS.setSpeechRate(mRate.getProgress()/10.0f);		//rate 설정.
                        m_TTSobj.speak(input, TextToSpeech.QUEUE_FLUSH, null);    //해당 언어로 텍스트 음성 출력
                    }
                }
            }
        });
        Spanned input_sp =(Spanned) TextUtils.concat(AACcItem.getSymbol(),AACcItem.getText());
        mtv_AACc.setText(input_sp);

        mtv_AACc.setBackgroundResource(AACcItem.getWho() ? R.drawable.bubble_a : R.drawable.bubble_b);
        m_SingleMessageContainer.setGravity(AACcItem.getWho() ? Gravity.LEFT : Gravity.RIGHT);
        return cv;
    }

}
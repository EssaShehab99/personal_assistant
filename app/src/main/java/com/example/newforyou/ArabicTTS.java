package com.example.newforyou;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class ArabicTTS {

    private static final String[] arabic = {"ء","أ","ؤ","ا","ئ","آ","ى","ب","ت","ث","ج","ح","خ","د","ذ","ر","ز","س","ش","ص","ض","ط","ظ","ع","غ","ف","ق","ك","ل","م","ن","ه","ة","و","ي","پ‎","چ","ڜ","ڥ","ڤ","ݣ","گ","ڨ","؟",",","!","۰","۱","٢","٣","٤","٥","٦","٧","٨","٩"};
    private static final String[] english = {"a","a","a","a","a","a","a","b","t","th","dj","h","kh","d","dh","r","z","s","sh","s","d","t","dh","a","gh","f","q","k","l","m","n","h","a","uo","y","p","ch","tch","v","v","g","g","g","?",",","!","0","1","2","3","4","5","6","7","8","9"};
    private static final String[] mistakes = {"bay","dai","sad","bar","kah","aaa","kau","oan","tuo","yam","gar"," uo","saf","maz","maw","yaw","wab","kas","mach","wak","has","zam","aya","mar","tan","sar","way "," man ","hawak","rad","i ","bay","law","way","lalah"," maw "," maw?","maw ","yar","tak","zab","nay","aay"," aa","nai"};
    private static final String[] fixes = {"bi","di","sed","ber","kh","aa","ku","on","tou","eym","gur"," wa","sif","muz","moo","eoo","ob","kos","mich","ok","hass","zom","aia","mer","taan","sur","oee "," min ","hook","red","y ","bi","loo","we","llah"," mo "," mo?","mo ","yer","tik","zeb","ni","ai"," a","ni"};

    private static final String[] arnumbers = {"0","١","1","2","3","4","5","6","7","8","9"};
    private static final String[] ennumbers = {" suffrr "," waheed "," waheed "," ethaneen "," sallassa "," arurbaa "," khamssa "," setta "," sabaa "," sammania "," tessaa "};

    private TextToSpeech tts;
    private String latest = "";
    public void prepare(Context con){
        if(con != null)
        {
            tts = new TextToSpeech(con, status -> {
                if (status != TextToSpeech.ERROR) {
                    Locale loc = new Locale("en", "IN");
                    tts.setLanguage(loc);
                    Set<String> a=new HashSet<>();
                    a.add("male");
                    Voice voice=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),1000,500,true,a);
                    tts.setVoice(voice);

                }
            });
        }
    }


    // To convert text to speech
    public void talk(String text){
        if(!text.equals(latest)) {
            text = filter(text);
        }
        if(tts!=null && text != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }


    public String filter(String text) {
        while(text.contains("  ")){
            text = text.replace("  "," ");
        }
        text = " "+text+" ";
        text = convert(text,1);
        text = convert(text,2);
        text = convert(text,3);
        latest = text;
        return text;
    }



    // Converting into latin
    private String convert(String text,int type){

        String[] fromlist = null;
        String[] tolist = null;

        if(type == 1){
            fromlist = arabic;
            tolist = english;
        }else if(type == 2){
            fromlist = mistakes;
            tolist = fixes;
        }else if(type == 3){
            fromlist = arnumbers;
            tolist = ennumbers;
        }

        for(int x = 0; x< Objects.requireNonNull(fromlist).length; x++){
            if(text.contains(fromlist[x])){
                if(type == 1) {
                    text = text.replace(fromlist[x], tolist[x] + "a");
                }else{
                    text = text.replace(fromlist[x], tolist[x]);
                }
            }
        }
        if(type == 1)
            text = text.replace("a "," ");
        return text;
    }

}

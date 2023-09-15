package live.nerotv.projectsbase.api;

import java.util.ArrayList;

public class ChatAPI {

    public static ArrayList<String> blockedStrings = new ArrayList<>();
    public static ArrayList<String> blockedNames = new ArrayList<>();
    public static ArrayList<String> blockedJobs = new ArrayList<>();

    public static boolean isJobBlocked(String DYM) {
        String Job = DYM.toLowerCase();
        if(Job.contains("bürgermeister")) {
            return true;
        } else if(Job.contains("buergermeister")) {
            return true;
        } else if(Job.contains("buerger meister")) {
            return true;
        } else if(Job.contains("bürger meister")) {
            return true;
        } else if(Job.contains("könig")) {
            return true;
        } else if(Job.contains("koenig")) {
            return true;
        } else if(Job.contains("buergermeisterin")) {
            return true;
        } else if(Job.contains("bürgermeisterin")) {
            return true;
        } else if(Job.contains("buerger meisterin")) {
            return true;
        } else if(Job.contains("bürger meisterin")) {
            return true;
        } else if(Job.contains("königin")) {
            return true;
        } else if(Job.contains("koenigin")) {
            return true;
        } else if(Job.contains("?")) {
            return true;
        } else if(Job.contains("/")) {
            return true;
        } else if(Job.contains("\\")) {
            return true;
        } else if(Job.contains("skuse")) {
            return true;
        } else if(Job.contains("a.s.b.")) {
            return true;
        } else if(Job.contains("geheim")) {
            return true;
        } else if(Job.contains("graf")) {
            return true;
        } else if(Job.contains("graph")) {
            return true;
        } else if(Job.contains("führer")) {
            return true;
        } else if(Job.contains("fuehrer")) {
            return true;
        } else if(Job.contains("president")) {
            return true;
        } else if(Job.contains("präsident")) {
            return true;
        } else if(Job.contains("praesident")) {
            return true;
        } else if(Job.contains("hure")) {
            return true;
        } else if(Job.contains("fotze")) {
            return true;
        } else if(Job.contains("schwanz")) {
            return true;
        } else if(Job.contains("sir")) {
            return true;
        } else if(Job.contains("lady")) {
            return true;
        } else if(Job.contains("attentaeter")) {
            return true;
        } else if(Job.contains("attentäter")) {
            return true;
        } else if(Job.contains("zwang")) {
            return true;
        } else if(Job.contains("skalve")) {
            return true;
        } else if(Job.contains("buergermeister")) {
            return true;
        } else if(Job.contains("gesetz")) {
            return true;
        } else if(Job.contains("poliz")) {
            return true;
        } else if(Job.contains("killer")) {
            return true;
        } else if(Job.contains("imperator")) {
            return true;
        } else if(Job.contains("lord")) {
            return true;
        } else if(Job.contains("lordt")) {
            return true;
        } else if(Job.contains("lort")) {
            return true;
        } else if(Job.contains("_")) {
            return true;
        } else if(Job.contains("fick")) {
            return true;
        } else if(Job.contains("fresse")) {
            return true;
        } else if(Job.contains("nutte")) {
            return true;
        } else if(Job.contains("sohn")) {
            return true;
        } else if(Job.contains("schwanz")) {
            return true;
        } else if(Job.contains("miliz")) {
            return true;
        } else if(Job.contains("sterben")) {
            return true;
        } else if(Job.contains("selbstmord")) {
            return true;
        } else if(Job.contains("suizid")) {
            return true;
        } else if(Job.contains("fuck")) {
            return true;
        } else if(Job.contains("you")) {
            return true;
        } else if(Job.contains("tube")) {
            return true;
        } else if(Job.contains("porn")) {
            return true;
        } else if(Job.contains("ideallauch")) {
            return true;
        } else if(Job.contains("nero")) {
            return true;
        } else if(Job.contains("schleimer")) {
            return true;
        } else if(Job.contains("nyuun")) {
            return true;
        } else if(Job.contains("nina")) {
            return true;
        } else if(Job.contains("gomme")) {
            return true;
        } else if(Job.contains("pewdie")) {
            return true;
        } else if(Job.contains("arsch")) {
            return true;
        } else {
            if(isStringBlocked(Job)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isNameBlocked(String DYM) {
        String Name = DYM.toLowerCase();
        if(isJobBlocked(Name)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStringBlocked(String DYM) {
        String string = DYM.toLowerCase();
        if(string.contains("nigga")) {
            return true;
        } else if(string.contains("niga")) {
            return true;
        } else if(string.contains("nega")) {
            if(string.contains("negativ")) {
                if(string.contains("ohne")) {
                    if(string.contains("tiv")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if(string.contains("neger")) {
            return true;
        } else if(string.contains("huso")) {
            return true;
        } else if(string.contains("n3ger")) {
            return true;
        } else if(string.contains("neg3r")) {
            return true;
        } else if(string.contains("n3g3r")) {
            return true;
        } else if(string.contains("n3gger")) {
            return true;
        } else if(string.contains("n3gg3r")) {
            return true;
        } else if(string.contains("negg3r")) {
            return true;
        } else if(string.contains("n3ga")) {
            return true;
        } else if(string.contains("n3gga")) {
            return true;
        } else if(string.contains("hur3nsohn")) {
            return true;
        } else if(string.contains("hur3ns0hn")) {
            return true;
        } else if(string.contains("hurens0hn")) {
            return true;
        } else if(string.contains("nigger")) {
            return true;
        } else if(string.contains("niger")) {
            if(string.contains("weniger")) {
                return false;
            } else {
                return true;
            }
        } else if(string.contains("nazi")) {
            return true;
        } else if(string.contains("hitler")) {
            return true;
        } else if(string.contains("hure")) {
            return true;
        } else if(string.contains("fotze")) {
            return true;
        } else if(string.contains("vergewalti")) {
            return true;
        } else if(string.contains("misgeburt")) {
            return true;
        } else if(string.contains("mistgeburt")) {
            return true;
        } else if(string.contains("missgeburt")) {
            return true;
        } else if(string.contains("misstgeburt")) {
            return true;
        } else if(string.contains("misset")) {
            return true;
        } else if(string.contains("miset")) {
            return true;
        } else if(string.contains("missed")) {
            return true;
        } else if(string.contains("mised")) {
            return true;
        } else if(string.contains("faggot")) {
            return true;
        } else if(string.contains("schwuchtel")) {
            return true;
        } else if(string.contains("spast")) {
            return true;
        } else if(string.contains("spasst")) {
            return true;
        } else if(string.contains("cancer")) {
            return true;
        } else if(string.contains("krebs")) {
            return true;
        } else if(string.contains("corona")) {
            return true;
        } else if(string.contains("corinski")) {
            return true;
        } else if(string.contains("atilla")) {
            return true;
        } else if(string.contains("hildmann")) {
            return true;
        } else if(string.contains("hildman")) {
            return true;
        } else if(string.contains("atila")) {
            return true;
        } else {
            return false;
        }
    }
}
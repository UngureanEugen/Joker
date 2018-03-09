package android.com.javajokes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Joker {
    private final Random random = new Random();
    public String getJoke() {
        return jokes.get(random.nextInt(jokes.size()));
    }

    private static List<String> jokes = Arrays.asList(
        "I can't believe I made it anywhere creatively, though, because I was raised by two loving and supportive parents. Nothing squashes creativity more than unconditional love and support from a functional household. If you have kids, sh*t on their dreams a little bit.",
        "A guy goes to the psychiatrist wearing shorts made of clear plastic wrap.The psychiatrist says, \"Well, I can clearly see you're nuts.\"",
        "Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.",
        "Sleep with an open window tonight! 1400 mosquitos like that. 420 mosquitos commented on it. 210 mosquitos shared this. One mosquito invited for the event. 2800 mosquitos will be attending the event.",
        "Me and my wife decided that we don't want to have children anymore. So anybody who wants one can leave us their phone number and address and we will bring you one.");
}

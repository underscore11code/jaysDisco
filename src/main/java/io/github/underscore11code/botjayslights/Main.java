package io.github.underscore11code.botjayslights;

import okhttp3.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main implements Runnable {
  private static final OkHttpClient okHttp = new OkHttpClient();
  private static final Random random = new Random();

  @Override
  public void run() {
    final FormBody fb = new FormBody.Builder()
            .add("color", randomHex())
            .add("state", "on")
            .add("brightness", String.valueOf(random.nextInt(101)))
            .build();

    final Call call = okHttp.newCall(new Request.Builder().url("https://api.jaywilliams.me/random_crap/set_light.php")
            .post(fb).build());

    try {
      final Response response = call.execute();
      System.out.println(response);
      response.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Executors.newSingleThreadScheduledExecutor()
            .scheduleAtFixedRate(new Main(), 0, 300, TimeUnit.MILLISECONDS);
  }

  public static String randomHex() {
    final StringBuilder sb = new StringBuilder("#");

    for (int i = 0; i < 6; i++) {
      sb.append(intToHex(random.nextInt(16)));
    }

    return sb.toString();
  }

  public static String intToHex(int i) {
    if (i < 10) {
      return String.valueOf(i);
    }

    switch (i) {
      case 10: return "A";
      case 11: return "B";
      case 12: return "C";
      case 13: return "D";
      case 14: return "E";
      case 15: return "F";
      default: throw new IllegalArgumentException();
    }
  }
}

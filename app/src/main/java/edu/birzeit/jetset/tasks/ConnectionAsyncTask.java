package edu.birzeit.jetset.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import edu.birzeit.jetset.api.HttpManager;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
//        ((MainActivity) activity).setButtonText("connecting");
//        super.onPreExecute();
//        ((MainActivity) activity).setProgress(true);
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        ((MainActivity) activity).setProgress(false);
//        ((MainActivity) activity).setButtonText("connected");
//        List<Student> students =
//                StudentJsonParser.getObjectFromJson(s);
//        ((MainActivity) activity).fillStudents(students);
    }
}

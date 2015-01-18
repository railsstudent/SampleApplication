package com.sample.sampleapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private static class PeopleManager {

        private static PeopleManager _instance = new PeopleManager();
        public static final int TASK_COMPLETE = 1;

        private Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                RetrievePeopleTask task = (RetrievePeopleTask) msg.obj;
                switch (msg.what) {
                    //  case
                    case PeopleManager.TASK_COMPLETE:
                        ListView lv = task.getListView();
                        List<Person> lstPerson = task.getLstPeople();
                        Context context = task.getContext();
                        PersonListAdapter adapter = new PersonListAdapter(context, lstPerson);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };


        private PeopleManager() {

        }

        public static PeopleManager getInstance() {
            return _instance;
        }

        public void handleState(RetrievePeopleTask task, int state) {
            switch (state) {
                case TASK_COMPLETE:
                    Message message = mHandler.obtainMessage(state, task);
                    message.sendToTarget();
                    break;
            }
        }
    }

    private static class RetrievePeopleTask {

        public static final int TASK_COMPLETE = 1;

        private List<Person> lstPeople;
        private ListView listView;
        private Context context;

        private PeopleManager manager = PeopleManager.getInstance();

        public RetrievePeopleTask(Context context, ListView listView) {
            this.context = context;
            this.listView = listView;
        }

        public Context getContext() {
            return context;
        }

        public void setLstPeople(List<Person> list) {
            this.lstPeople = list;
        }

        public ListView getListView() {
            return this.listView;
        }

        public List<Person> getLstPeople() {
            return lstPeople;
        }

        public void handleRetrieve(int state) {
            switch (state) {
                case TASK_COMPLETE:
                    manager.handleState(this, PeopleManager.TASK_COMPLETE);
                    break;
                default:
                    break;
            }
        }

        
    }

    private static class GetPeopleRunnable implements Runnable {

        private RetrievePeopleTask task;

        public GetPeopleRunnable(RetrievePeopleTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            List<Person> lstPerson = PersonServiceHelper.getPeople();
            task.setLstPeople(lstPerson);
            task.handleRetrieve(RetrievePeopleTask.TASK_COMPLETE);

        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            ListView lv = (ListView) view.findViewById(R.id.listView);
            RetrievePeopleTask task = new RetrievePeopleTask(getActivity(), lv);
            GetPeopleRunnable runnable = new GetPeopleRunnable(task);

            new Thread(runnable).start();
        }
    }
}

package com.example.levchukapplication.services

import android.os.AsyncTask
import kotlin.random.Random
import kotlin.random.nextInt

class SimpleAsyncTask(val onTick: (Int)->Unit, val onInfo:(String)->Unit): AsyncTask<Void, Int,String>() {
    var sum: Int = 0

    override fun doInBackground(vararg p0: Void?): String {
       while(sum<10000){
           val n =Random.nextInt(1..11)
           val s=n*100
           sum+=s
           try {
               Thread.sleep(s.toLong())
               publishProgress(sum)
           } catch (ex: InterruptedException){
               ex.printStackTrace()
           }
       }
        return "Completed the task after $sum miliseconds"
    }

    override fun onPostExecute(result: String?) {
      onInfo(result.toString())
    }

    override fun onPreExecute() {
        super.onPreExecute()
        onInfo("Task started")
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val pct=sum/100
        onTick(pct)
    }
}
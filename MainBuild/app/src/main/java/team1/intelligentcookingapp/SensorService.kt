package team1.intelligentcookingapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder

class SensorService : Service(), SensorEventListener {
    fun SensorService() {

    }

    internal var xAccel: Float = 0.toFloat()
    internal var yAccel: Float = 0.toFloat()
    internal var zAccel: Float = 0.toFloat()

    internal var xPreviousAccel: Float = 0.toFloat()
    internal var yPreviousAccel: Float = 0.toFloat()
    internal var zPreviousAccel: Float = 0.toFloat()

    internal var firstUpdate = true
    internal var shakeInitiated = false
    internal var shakeThreshold = 12.0f
    internal var clearIngredients = false

    internal var accelormeter: Sensor? = null
    internal var sensorMng: SensorManager? = null


    inner class MyLocalBinder : Binder() {
        fun getService() : SensorService {
            return this@SensorService
        }

    }

    private val myBinder = MyLocalBinder()

    private var mSensors: Sensor? = null
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        this.sensorMng = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorMng!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            this.accelormeter = it
        }
        sensorMng!!.registerListener(this, accelormeter, SensorManager.SENSOR_DELAY_NORMAL)

        clearIngredients = false

        return Service.START_STICKY
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {

        updateAccelParameter(event.values[0], event.values[1], event.values[2])

        if (!shakeInitiated && isAccelerationisChanged()) {
            shakeInitiated = true
            clearIngredients = false
        } else if (shakeInitiated && isAccelerationisChanged()) {
            clearIngredients = true
        }
        if (!shakeInitiated && !isAccelerationisChanged()) {
            shakeInitiated = true
            clearIngredients = false
        }

    }

    fun shakeDetected(): Boolean {
        return clearIngredients
    }


    private fun isAccelerationisChanged(): Boolean {
        var flag = false

        val deltaX = Math.abs(xPreviousAccel - xAccel)
        val deltaY = Math.abs(yPreviousAccel - yAccel)
        val deltaZ = Math.abs(zPreviousAccel - zAccel)

        if (deltaX > shakeThreshold && deltaY > shakeThreshold
            || deltaX > shakeThreshold && deltaZ > shakeThreshold
            || deltaY > shakeThreshold && deltaZ > shakeThreshold
        )
        {
            flag = true
        }
        else {
            flag = false
        }


        return flag
    }

    private fun updateAccelParameter(xNewAccel: Float, yNewAccel: Float, zNewAccel: Float) {

        if (firstUpdate == true) {
            xPreviousAccel = xNewAccel
            yPreviousAccel = yNewAccel
            zPreviousAccel = zNewAccel
            firstUpdate = false
        } else {
            xPreviousAccel = xAccel
            yPreviousAccel = yAccel
            zPreviousAccel = zAccel
        }

        xAccel = xNewAccel
        yAccel = yNewAccel
        zAccel = zNewAccel

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return myBinder
    }
}

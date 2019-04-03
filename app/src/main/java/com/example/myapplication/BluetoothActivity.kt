package com.example.myapplication


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myapplication.R
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothActivity : AppCompatActivity() {
    companion object {
        lateinit var mDevice: BluetoothDevice
        lateinit var mBluetoothAdapter: BluetoothAdapter
        val HC05_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        val MAC_ADDRESS:String="00:18:E4:40:00:06"
        //val MAC_ADDRESS:String="98:D3:61:FD:4D:81"//troque pelo seu MACADDRESS
        var mConnectThread: ConnectThread? = null
        var mConnectedThread: ConnectedThread? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        //Bluetooth do celular
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        //obtem os dispositivos pareados e coloca na lista pairedDevices
        val pairedDevices = mBluetoothAdapter!!.getBondedDevices()
        for (device in pairedDevices) {
            if (device.address.toString() == MAC_ADDRESS) {
                Log.d("mer", "Conectou em " +device.address.toString())
                mDevice = device
                mConnectThread = ConnectThread(mDevice)
                mConnectThread!!.start()
                break //sai do laço for
            }

        }
        //finalmente vamos conectar o arduino....

    }

    fun ClickButton1(view:  View) {
        val ch = 'P'
        val bt = ch.toByte()
        mConnectedThread!!.write(bt)

        // var intent: Intent = Intent(this, Biblioteca::class.java)
        // startActivity(intent)
    }

    fun ClickButton2(view: View) {
        val ch = 'D'
        val bt = ch.toByte()
        mConnectedThread!!.write(bt)
        // var intent: Intent = Intent(this, Config::class.java)
        // startActivity(intent)
    }



    /* Aqui são as classes que fazem a comunicação*/
    //Classe para criar a conexão com o bluetooth
    inner class ConnectThread : Thread {
        var mmSocket: BluetoothSocket?;
        var mmDevice: BluetoothDevice?;

        constructor (device: BluetoothDevice) {
            Log.d("mer","Iniciou o construtor")
            var tmp: BluetoothSocket? = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(HC05_UUID);
            } catch (e: IOException) {
                finish()
            }
            mmSocket = tmp;
            Log.d("mer","Socket "+mmSocket)
        }

        override fun run() {
            mConnectedThread = null;
            mBluetoothAdapter!!.cancelDiscovery()
            try {
                mmSocket!!.connect();
                mConnectedThread =ConnectedThread (mmSocket!!)
                mConnectedThread!!.start()
            } catch (connectException: IOException) {
                Log.d("mer","Erro "+connectException)
                try {
                    mmSocket!!.close();
                } catch (closeException: IOException) {
                    cancel()
                    finish()
                }
                return;
            }
        }

        public fun cancel() {
            try {
                mmSocket!!.close()
            } catch (e: IOException) {
            }

        }
    }
    //classe para gerenciar a conexão com o bluetooth
    inner  class ConnectedThread(private val mmSocket: BluetoothSocket) : Thread() {

        private val mmOutStream: OutputStream?

        init {
            var tmpOut: OutputStream? = null
            try {
                tmpOut = mmSocket.outputStream
                Log.d("mer","OutPutStream "+tmpOut)
            } catch (e: IOException) {
                Log.d("mer","OutPutStream "+e)
            }
            mmOutStream = tmpOut
        }
        fun write(bytes: Byte) {
            try {
                mmOutStream!!.write(bytes.toInt())
            } catch (e: IOException) {
                cancel()
                finish()
            }
        }
        public fun cancel() {
            try {
                mmOutStream!!.close()
                mmSocket.close()

            } catch (e: IOException) {
            }
        }
    }


}



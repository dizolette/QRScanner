package com.geniuslabs.qrscanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : AppCompatActivity(),
ZXingScannerView.ResultHandler, View.OnClickListener {

    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScannerView()
        initDefaultView()
        button_reset.setOnClickListener(this)

    }

    private fun initDefaultView() {
        //To change body of created functions use File | Settings | File Templates.
        text_view_qr_code_value.text = "QR Code Value"
        button_reset.visibility = View.GONE
    }

    private fun initScannerView() {
        //To change body of created functions use File | Settings | File Templates.
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }

    private fun doRequestPermission() {
        //To change body of created functions use File | Settings | File Templates.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA),100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            100->{
                initScannerView()
            }
            else ->{

            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    override fun handleResult(rawResult: Result?) {
        //To change body of created functions use File | Settings | File Templates.
        text_view_qr_code_value.text = rawResult?.text
        button_reset.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        //To change body of created functions use File | Settings | File Templates.
        when(v?.id){
            R.id.button_reset -> {
                mScannerView.resumeCameraPreview(this)
                initDefaultView()
            }
            else -> {

            }
        }
    }

}

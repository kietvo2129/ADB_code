package com.autonsi.databoard.Receving.ReceivingOder.OderInformation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Barcode.BarcodeAdapter;
import com.autonsi.databoard.Barcode.BarcodeMaster;
import com.autonsi.databoard.Counting.Count.DialogManager;
import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class OrderInforActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;

    ArrayList<OrderInformationMaster> orderInformationMasterArrayList;
    ArrayList<OrderInformationMaster> orderInformationMasterArrayListnew;
    static BixolonLabelPrinter mBixolonLabelPrinter;
    RecyclerView rycview;
    OrderInformationAdapter orderInformationAdapter;
    private String mConnectedDeviceName = null;
    ArrayList<BarcodeMaster> barcodeMasterArrayList;
    BarcodeAdapter barcodeAdapter;
    private final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private UsbDevice device;
    private boolean tryedAutoConnect = false;
    private UsbManager usbManager;
    private PendingIntent mPermissionIntent;

    int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_infor);
        rycview = findViewById(R.id.rycview);


        getOrderInfor();
    }

    private void getOrderInfor() {
        http:
//dba.autonsi.com/

        new getOrderInfor().execute(Url + "WMSReceiving/GetOrderMaterials?_search=false&nd=1600235178274&rows=20&page=1&sidx=&sord=asc");
    }

    private class getOrderInfor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            orderInformationMasterArrayList = new ArrayList<>();
            orderInformationMasterArrayListnew = new ArrayList<>();
            try {

                JSONArray jsonArray = new JSONArray(s);

                String id, mt_cd, mt_cd_origin, mt_nm, mt_type, unit_cd, bundle_qty, sp_cd, qc_cd, qc_range, unit_price, bundle_unit_price, currency_code, image, re_mark, use_yn, reg_dt, expiry_days, sp_nm, sp_type, bundle_price, storage_qty, lct_cd, status, qty_bundle, qc_name, currency_name, type_name, unit_name, status_name;

                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", OrderInforActivity.this);
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("id").replace("null", "");
                        mt_cd = jsonObject.getString("mt_cd").replace("null", "");
                        mt_cd_origin = jsonObject.getString("mt_cd_origin").replace("null", "");
                        mt_nm = jsonObject.getString("mt_nm").replace("null", "");
                        mt_type = jsonObject.getString("mt_type").replace("null", "");
                        storage_qty = jsonObject.getString("storage_qty").replace("null", "");
                        lct_cd = jsonObject.getString("lct_cd").replace("null", "");
                        status = jsonObject.getString("status").replace("null", "");
                        qty_bundle = jsonObject.getString("qty_bundle").replace("null", "");
                        qc_name = jsonObject.getString("qc_name").replace("null", "");
                        status_name = jsonObject.getString("status_name").replace("null", "");
                        unit_cd = jsonObject.getString("unit_cd").replace("null", "");
                        bundle_qty = jsonObject.getString("bundle_qty").replace("null", "");
                        sp_cd = jsonObject.getString("sp_cd").replace("null", "");
                        qc_cd = jsonObject.getString("qc_cd").replace("null", "");
                        qc_range = jsonObject.getString("qc_range").replace("null", "");
                        unit_price = jsonObject.getString("unit_price").replace("null", "");
                        bundle_unit_price = jsonObject.getString("bundle_unit_price").replace("null", "");
                        currency_code = jsonObject.getString("currency_code").replace("null", "");
                        image = jsonObject.getString("image").replace("null", "");
                        use_yn = jsonObject.getString("use_yn").replace("null", "");
                        expiry_days = jsonObject.getString("expiry_days").replace("null", "");
                        sp_nm = jsonObject.getString("sp_nm").replace("null", "");
                        sp_type = jsonObject.getString("sp_type").replace("null", "");
                        bundle_price = jsonObject.getString("bundle_price").replace("null", "");
                        re_mark = jsonObject.getString("re_mark").replace("null", "");
                        currency_name = jsonObject.getString("currency_name").replace("null", "");
                        type_name = jsonObject.getString("type_name").replace("null", "");
                        unit_name = jsonObject.getString("unit_name").replace("null", "");
                        reg_dt = jsonObject.getString("reg_dt").replace("null", "");

                        orderInformationMasterArrayList.add(new OrderInformationMaster(id, mt_cd, mt_cd_origin, mt_nm, mt_type, unit_cd, bundle_qty
                                , sp_cd, qc_cd, qc_range, unit_price, bundle_unit_price, currency_code
                                , image, re_mark, use_yn, reg_dt, expiry_days, sp_nm, sp_type, bundle_price
                                , storage_qty, lct_cd, status, qty_bundle, qc_name, currency_name, type_name
                                , unit_name, status_name));

                    }
                    orderInformationMasterArrayListnew = orderInformationMasterArrayList;
                    buildRecyclerView();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", OrderInforActivity.this);
            }
        }
    }

    private void buildRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager;
        rycview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(OrderInforActivity.this);
        orderInformationAdapter = new OrderInformationAdapter(orderInformationMasterArrayList);
        rycview.setLayoutManager(mLayoutManager);
        rycview.setAdapter(orderInformationAdapter);

        orderInformationAdapter.setOnItemClickListener(new OrderInformationAdapter.OnItemClickListener() {
            @Override
            public void onwarningClick(int position) {
                popupAction(position);
            }
        });

        mBixolonLabelPrinter = new BixolonLabelPrinter(this, mHandler, Looper.getMainLooper());

        final int ANDROID_NOUGAT = 24;
        if (Build.VERSION.SDK_INT >= ANDROID_NOUGAT) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    private void popupAction(int position) {

        if (orderInformationMasterArrayList.get(position).getStatus_name().equals("Order")){
            hoiinbacode( position);
        }else {
            String namedevice = android.os.Build.MODEL;
            if (namedevice.equals("D1s")) {
                openPrint(1,position);
                vitri = position;
            }else {
                AlerError.Baoloi("Device not support!",this);
            }
        }

    }

    private void openPrint(int vt,int pos) {
        new creat_Barcode_print().execute(Url+"WMSReceiving/GetMaterialLotCode?page=" + vt + "&rows=50&sidx=&sord=asc&_search=false&mtCode="+ orderInformationMasterArrayList.get(pos).getMt_cd());
    }

    private class creat_Barcode_print extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barcodeMasterArrayList =new ArrayList<>();
            String id,barcode;
            int total = 0,page= 0,records= 0;
            try {

                JSONObject jsonObject = new JSONObject(s);

                total = jsonObject.getInt("total");
                page = jsonObject.getInt("page");
                records = jsonObject.getInt("records");


                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                if (jsonArray.length() == 0){
                    return;
                }
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    id = jsonObject1.getString("id").replace("null","");
                    barcode = jsonObject1.getString("mt_lot_cd").replace("null","");
                    barcodeMasterArrayList.add(new BarcodeMaster(id,barcode,50* page -50 + i + 1 + "",true));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            call_Popup_barcode(total,page,records);

        }

    }

    private void call_Popup_barcode(int total,int page, int records) {
        Dialog dialog = new Dialog(OrderInforActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        View dialogView = LayoutInflater.from(OrderInforActivity.this).inflate(R.layout.popup_barcode, null);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        RecyclerView recyclerViewCL;
        recyclerViewCL = dialogView.findViewById(R.id.recyclerViewCL);

        recyclerViewCL.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(OrderInforActivity.this);
        barcodeAdapter = new BarcodeAdapter(barcodeMasterArrayList);
        recyclerViewCL.setLayoutManager(mLayoutManager);
        recyclerViewCL.setAdapter(barcodeAdapter);

        dialog.findViewById(R.id.xacnhanin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_tem();
            }
        });

        TextView tvback,tvview,tvnext;

        tvback  =dialog.findViewById(R.id.tvback);
        tvview  =dialog.findViewById(R.id.textview);
        tvnext  =dialog.findViewById(R.id.tvnext);

        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page -1 <=0){
                    return;
                }else {
                    dialog.dismiss();
                    openPrint(page-1,vitri);
                }
            }
        });
        tvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page + 1 > total){
                    return;
                }else {
                    dialog.dismiss();
                    openPrint(page + 1,vitri);
                }
            }
        });

        tvview.setText("Page: " + page + "/" + total + " View " + (50*page - 49) + " - "+ 50*page + " of "+ records);
        dialog.show();
    }

    private void in_tem() {
        final boolean hasResponse = false;
        final int responseLength = 0;

        for (int i =0;i<barcodeMasterArrayList.size();i++){

            if (barcodeMasterArrayList.get(i).isChecked()) {
                String command =
                        "CB\n" +
                                "SS3\n" +    // Set Speed to 5 ips
                                "SD15\n" +    // Set Density level to 20
                                "SW320\n" +    // Set Label Width 320//800
                                //"SOT\n" +    // Set Printing Orientation from Top to Bottom
                                //"BD0,0,320,232,B,3\n" +   //Box thikness 5
                                //"T50,20,4,1,1,0,0,N,N,'" + "tieu de" + "'\n" +    //text 1. (4)Font - 15 pt
                                "B2110,30,Q,3,M,4,0,'" + barcodeMasterArrayList.get(i).getBarcode() + "'\n" +        //Qr .
                                "T20,150,1,1,1,0,0,N,N,'" + barcodeMasterArrayList.get(i).getBarcode() + "'\n" +        //text 3 (2)Font - 10 pt
                                "P1";
                if (command.length() > 0) {
                   OrderInforActivity.mBixolonLabelPrinter.executeDirectIo(command, hasResponse, responseLength);
                }
            }

        }

    }

    public void hoiinbacode(int pos) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderInforActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Would you like to print this Code?"); //"The data you entered does not exist on the server !!!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new LotCodePrint().execute(Url + "WMSReceiving/CreateMaterialLotCode?wMIds=" + orderInformationMasterArrayList.get(pos).getId());
            }
        });
        alertDialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }
    private class LotCodePrint extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")){
                    AlerError.Baoloi(jsonObject.getString("message"), OrderInforActivity.this);
                }else {
                    getOrderInfor();
                    Toast.makeText(OrderInforActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", OrderInforActivity.this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setFocusable(true);
        searchView.setQueryHint("Search MT Name");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String value) {
        ArrayList<OrderInformationMaster> filteredList = new ArrayList<>();
        for (OrderInformationMaster item : orderInformationMasterArrayListnew) {
            if (item.getMt_nm().toLowerCase().contains(value.toLowerCase())) {
                filteredList.add(item);
            }
        }
        orderInformationMasterArrayList = filteredList;
        orderInformationAdapter.filterList(filteredList);
    }
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BixolonLabelPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BixolonLabelPrinter.STATE_CONNECTED:
                            //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //mListView.setEnabled(true);
                            //mIsConnected = true;
                            invalidateOptionsMenu();
                            OrderInforActivity.mBixolonLabelPrinter.setOrientation(BixolonLabelPrinter.ORIENTATION_TOP_TO_BOTTOM);
                            break;

                        case BixolonLabelPrinter.STATE_CONNECTING:
                            // setStatus(getString(R.string.title_connecting));
                            break;

                        case BixolonLabelPrinter.STATE_NONE:
                            // setStatus(getString(R.string.title_not_connected));
                            //mListView.setEnabled(false);
                            //mIsConnected = false;
                            invalidateOptionsMenu();
                            break;
                    }
                    break;

                case BixolonLabelPrinter.MESSAGE_READ:
                    // CountActivity.this.dispatchMessage(msg);
                    break;

                case BixolonLabelPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(BixolonLabelPrinter.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_TOAST:
                    //mListView.setEnabled(false);
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.TOAST), Toast.LENGTH_SHORT).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_LOG:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.LOG), Toast.LENGTH_SHORT).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showBluetoothDialog(OrderInforActivity.this, (Set<BluetoothDevice>) msg.obj);
                    }
                    break;

                case BixolonLabelPrinter.MESSAGE_USB_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No connected device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showUsbDialog(OrderInforActivity.this, (Set<UsbDevice>) msg.obj, mUsbReceiver);
                    }
                    break;

                case BixolonLabelPrinter.MESSAGE_NETWORK_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No connectable device", Toast.LENGTH_SHORT).show();
                    }
                    DialogManager.showNetworkDialog(OrderInforActivity.this, (Set<String>) msg.obj);
                    break;

            }
        }
    };
    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                mBixolonLabelPrinter.connect();
                Toast.makeText(getApplicationContext(), "Found USB device", Toast.LENGTH_SHORT).show();
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                mBixolonLabelPrinter.disconnect();
                Toast.makeText(getApplicationContext(), "USB device removed", Toast.LENGTH_SHORT).show();
            } else if (ACTION_USB_PERMISSION.equals(action)) {
                mBixolonLabelPrinter.connect(device);
                device = null;
            }

        }
    };
    @Override
    public void onResume() {
        super.onResume();

        if (!tryedAutoConnect) {
            isConnectedPrinter();
        }
    }
    private void isConnectedPrinter() {
        try {
            usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            mPermissionIntent = PendingIntent.getBroadcast(this, 0,
                    new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_UPDATE_CURRENT);
            while (deviceIterator.hasNext()) {
                UsbDevice d = deviceIterator.next();
                if (d != null) {
                    for (int i = 0; i < d.getInterfaceCount(); i++) {
                        UsbInterface usbInterface = d.getInterface(i);
                        if (usbInterface.getInterfaceClass() == 7 && usbInterface.getInterfaceSubclass() == 1 && usbInterface.getInterfaceProtocol() == 2) {
                            device = d;
                            break;
                        }
                    }
                }
            }

            IntentFilter filter = new IntentFilter(
                    ACTION_USB_PERMISSION);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            registerReceiver(mUsbReceiver, filter);
            if (device != null) {
                usbManager.requestPermission(device, mPermissionIntent);
                tryedAutoConnect = true;
                Log.e("Exception", "Printer connected");
            } else {
                Log.e("Exception", "Printer not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
package ir.androidexception.filepicker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ir.androidexception.filepicker.R;
import ir.androidexception.filepicker.adapter.FileAdapter;
import ir.androidexception.filepicker.databinding.DialogPickerBinding;
import ir.androidexception.filepicker.interfaces.OnCancelPickerDialogListener;
import ir.androidexception.filepicker.interfaces.OnConfirmDialogListener;
import ir.androidexception.filepicker.interfaces.OnPathChangeListener;
import ir.androidexception.filepicker.interfaces.OnSelectItemListener;
import ir.androidexception.filepicker.model.Item;
import ir.androidexception.filepicker.utility.Util;


public class MultiFilePickerDialog extends Dialog implements OnPathChangeListener, OnSelectItemListener {
    private RecyclerView recyclerViewDirectories;
    private FloatingActionButton fab;
    private ImageView close;
    private Context context;
    private DialogPickerBinding binding;
    private FileAdapter adapter;
    private OnCancelPickerDialogListener onCancelPickerDialogListener;
    private OnConfirmDialogListener onConfirmDialogListener;
    private List<File> files;
    private List<String> allowedFileTypes = null;

    public MultiFilePickerDialog(@NonNull Context context, OnCancelPickerDialogListener onCancelPickerDialogListener,
                                 OnConfirmDialogListener onConfirmDialogListener) {
        super(context);
        this.context = context;
        this.onCancelPickerDialogListener = onCancelPickerDialogListener;
        this.onConfirmDialogListener = onConfirmDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_picker, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        recyclerViewDirectories = binding.rvDialogPickerDirectories;
        fab = binding.fab;
        close = binding.ivClose;

        if (Util.permissionGranted(context)) {
            binding.setPath("Internal Storage" + context.getString(R.string.arrow));
            binding.setBusySpace(Util.bytesToHuman(Util.busyMemory()));
            binding.setTotalSpace(Util.bytesToHuman(Util.totalMemory()));
            int busySpacePercent = (int) (((float) Util.busyMemory() / Util.totalMemory()) * 100);
            binding.setBusySpacePercent(busySpacePercent + "%");
            binding.progressView.setProgress(busySpacePercent);
            files = new ArrayList<>();


            setupDirectoriesListRecyclerView();
            setupClickListener();
        }
    }


    private void setupClickListener() {
        close.setOnClickListener(v -> {
            onCancelPickerDialogListener.onCanceled();
            this.cancel();
        });

        fab.setOnClickListener(v -> {
            File[] fs = new File[files.size()];
            for (int i = 0; i < files.size(); i++) fs[i] = files.get(i);
            onConfirmDialogListener.onConfirmed(fs);
            this.cancel();
        });

        fab.setOnLongClickListener(v -> {
            adapter.selectAllFiles();
            return true;
        });
    }

    private void setupDirectoriesListRecyclerView() {
        List<Item> items = new ArrayList<>();
        File internalStorage = Environment.getExternalStorageDirectory();
        List<File> children = new ArrayList<>(Arrays.asList(Objects.requireNonNull(internalStorage.listFiles())));
        for (File file : children) {
            if(file.isFile() && allowedFileTypes != null) {
                for(String allowedFileType : allowedFileTypes) {
                    if(file.getAbsolutePath().endsWith(allowedFileType)) {
                        items.add(new Item(file));
                        break;
                    }
                }
            } else {
                items.add(new Item(file));
            }
        }

        adapter = new FileAdapter(context, items, this, this);
        adapter.setMultiFileSelect(true);
        adapter.setAllowedFileTypes(allowedFileTypes);
        recyclerViewDirectories.setAdapter(adapter);
        recyclerViewDirectories.setNestedScrollingEnabled(false);
    }

    public MultiFilePickerDialog setAllowedFileTypes(List<String> allowedFileTypes) {
        this.allowedFileTypes = allowedFileTypes;
        return this;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onBackPressed() {
        adapter.back();
    }

    @Override
    public void onChanged(String path) {
        binding.setPath(Util.changePathFormat(context, path));
    }

    @Override
    public void onSelected(File f) {
        if (files.contains(f)) files.remove(f);
        else files.add(f);

        if (files.isEmpty()) fab.setVisibility(View.GONE);
        else fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMultiSelect(File f) {
        if (!files.contains(f)) {
            files.add(f);
        }
    }
}

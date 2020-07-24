package ir.androidexception.filepicker.interfaces;

import java.io.File;

public interface OnSelectItemListener {
    void onSelected(File file);

    void onMultiSelect(File file);
}

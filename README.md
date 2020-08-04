FileAndFolderPicker ![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat) [![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)
===================

This is a library for create a picker in your android application. You can pick a single or multiple file. Also you can pick folder with it.
  



**This library is also available at JitPack.io**

[![](https://jitpack.io/v/gmanrainy/FileAndFolderPicker.svg)](https://jitpack.io/#gmanrainy/FileAndFolderPicker)



`this library is compatible to androidx`

## Preview
![screenshot 1](https://github.com/salehyarahmadi/FileAndFolderPicker/blob/master/1.jpg)

## Setup
The simplest way to use this library is to add the library as dependency to your build.

## Gradle

Step 1. Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
	repositories {
		maven { url "https://maven.google.com" }
                google()
                jcenter()
                maven { url 'https://jitpack.io' }
		...
	}
}
```

Step 2. Add the dependency
  
```gradle
// builde.gradle(app level)
android{
	...
	compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
        }
        dataBinding {
                enabled = true
        }
}

dependencies {
    implementation 'com.github.salehyarahmadi:FileAndFolderPicker:1.0.4'
}
```
 


## Usage

### Java

For select single file
```java
if(permissionGranted()) {
    SingleFilePickerDialog singleFilePickerDialog = new SingleFilePickerDialog(this,
            () -> Toast.makeText(MainActivity.this, "Canceled!!", Toast.LENGTH_SHORT).show(),
            files -> Toast.makeText(MainActivity.this, files[0].getPath(), Toast.LENGTH_SHORT).show());
    singleFilePickerDialog.show();
}
else{
    requestPermission();
}
```

For select multiple files
```java
if(permissionGranted()) {
    MultiFilePickerDialog multiFilePickerDialog = new MultiFilePickerDialog(this,
            () -> Toast.makeText(MainActivity.this, "Canceled!!", Toast.LENGTH_SHORT).show(),
            files -> Toast.makeText(MainActivity.this, files[0].getPath(), Toast.LENGTH_SHORT).show()
    );
    multiFilePickerDialog.show();
}
else{
    requestPermission();
}
```

For select directory
```java
if(permissionGranted()) {
    DirectoryPickerDialog directoryPickerDialog = new DirectoryPickerDialog(this,
            () -> Toast.makeText(MainActivity.this, "Canceled!!", Toast.LENGTH_SHORT).show(),
            files -> Toast.makeText(MainActivity.this, files[0].getPath(), Toast.LENGTH_SHORT).show()
    );
    directoryPickerDialog.show();
}
else{
    requestPermission();
}
```

Permissions
```java
private boolean permissionGranted(){
    return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
}
private void requestPermission(){
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
}
```

In all types, you receive an array of files. In case of single file and directory picker, you have to use index 0 of files array. But in multiple files picker you can use all indices of array.

    
    


    


        
 ## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2019 DataTable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
       

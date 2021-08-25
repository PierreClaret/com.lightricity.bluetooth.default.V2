# com.Lightricity.bluetooth.default.V2
New version of the bluetooth library for decoding Nizar Format

### Nizar specific format:
https://github.com/PierreClaret/com.lightricity.bluetooth.default.V2/blob/master/Lightricity_Beacon_Format.pdf

# Usage example

Add the JitPack repository to your projects build.gradle file
```gradle
allprojects {
    repositories {
        maven { url "https://www.jitpack.io" }
        ...
    }
}
```

Add the library and its interface to your project by adding following as dependencies in app/build.gradle
```gradle
dependencies {
    implementation project(':bluetooth_library')
    implementation project(':default_bluetooth_library')
    ...
}
```

Define the project path in the settings.gradle file
```include ':default_bluetooth_library', ':MPChartLib',':bluetooth_library'
project(':default_bluetooth_library').projectDir=new File('../com.lightricity.bluetooth.default.V2/default_bluetooth_library')
project(':bluetooth_library').projectDir=new File('../com.lightricity.bluetooth.V2/bluetooth_library')
include ':app'
}
```

Add bluetooth and location permission in AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

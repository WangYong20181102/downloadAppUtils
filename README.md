# downloadAppUtils
app下载更新

Project(build.gradle)配置
allprojects {
    repositories {
        ..
        maven { url 'https://www.jitpack.io' }
        
    }
}

Module(build.gradle)配置
dependencies {
    ..
    implementation 'com.github.WangYong20181102:downloadAppUtils:v1.0'
}

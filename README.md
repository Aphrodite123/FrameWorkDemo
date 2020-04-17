# FrameWorkDemo
Help developers quickly build a simple project framework. 

[ ![Download](https://api.bintray.com/packages/aphrodite/maven/framework-base/images/download.svg?version=1.0.4) ](https://bintray.com/aphrodite/maven/framework-base/1.0.4/link)

## Dependency

Add this in your root `build.gradle` file (not your module `build.gradle` file):

```gradle
allprojects {  
        repositories {  
             maven { url "https://dl.bintray.com/aphrodite/maven" }
        }
}
```

Then, add the library to your module `build.gradle`

```gradle
dependencies {  
    implementation 'com.aphrodite.framework:framework-base:1.0.4'
}
```

## Features
- Use MVP architecture for most developers.
- At the same time the use of singletons, builder and other Java design patterns.

## Usage

## Usage with Fresco

## Subsampling Support

## License
Copyright 2018 Chris Banes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

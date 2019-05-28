# FrameWorkDemo
Help developers quickly build a simple project framework.
***

# Dependency

Add this in your root build.gradle file (not your module build.gradle file):

allprojects {	
	>repositories {
		>>mavenLocal()
		>>maven { url "https://jitpack.io" }
    }
}

Then, add the library to your module build.gradle

dependencies {
    implementation 'com.aphrodite.framework:framework-base:1.0.1'
}

# Features

# Usage

# Usage with Fresco

# Subsampling Support

# License
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

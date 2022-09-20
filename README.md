# edtsds
android edts design system

## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edtslib:edtsds:latest'
}
```

## List of components

# [1. Primary/Secondary Color](#Color)
# [2. AlertBoxView](#AlertBoxView)
# [3. ButtonView](#ButtonView)
# [4. TextFieldView](#TextFieldView)

# Color

Please override colors (if need)

```xml
    <!-- primary -->

    <color name="colorPrimary50">#044B95</color>
    <color name="colorPrimary40">#0958AA</color>
    <color name="colorPrimary30">#1178D4</color>
    <color name="colorPrimary20">#368BE2</color>
    <color name="colorPrimary10">#6CA5E0</color>

    <!-- secondary -->

    <color name="colorSecondary50">#CC8000</color>
    <color name="colorSecondary40">#DA8D0B</color>
    <color name="colorSecondary30">#F29D0D</color>
    <color name="colorSecondary20">#F0AF42</color>
    <color name="colorSecondary10">#EEC786</color>
```

# AlertBoxView

#### Usage

```xml
<id.co.edtslib.AlertBoxView
    app:type="success"
    app:dismissible="true"
    app:message="abah test"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:type_
[enum]: enum of alert type: success, warning error\
alert:\
![AlertBoxView](https://i.ibb.co/9wQNKKv/error-alert.png)

warning:\
![AlertBoxView](https://i.ibb.co/QrBfXHr/warning-alert.png)

success:\
![AlertBoxView](https://i.ibb.co/cQdSwt8/success-alert.png)

##### _app:message_
[string]: message of alert

##### _app:dismissible_
[dismissible]: alert box can dismissible or not, default true

#### Method for set message on the AlertBoxView

```kotlin

var message: String? = null
```

# ButtonView

#### Usage

```xml
<id.co.edtslib.ButtonView
    app:size="medium"
    app:variant="primary"
    android:id="@+id/buttonView"
    android:text="@string/app_name"
    android:layout_margin="16dp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:size_
[enum]: enum of button size: medium, small, default medium\
medium:\
![ButtonView](https://i.ibb.co/p1CQ5qL/button-medium.png)

small:\
![ButtonView](https://i.ibb.co/98K5XGp/button-small.png)

##### _app:variant_
[enum]: enum of button variant: primary, secondary, outline, negative, default primary

primary:\
![ButtonView](https://i.postimg.cc/8zYyCqmf/button-primary.png)

secondary:\
![ButtonView](https://i.postimg.cc/XqNKL8g0/button-secondary.png)

alternative:\
![ButtonView](https://i.postimg.cc/K8TTnwyM/button-outline.png)

outline:\
![ButtonView](https://i.postimg.cc/L5zXqKP9/button-negative.png)

# TextFieldView
![TextFieldView](https://i.postimg.cc/7ZnvjyWh/Text-Field-View.png)

#### Usage

```xml
    <id.co.edtslib.edtsds.textfield.TextFieldView
        android:hint="password"
        app:inputType="password"
        android:layout_margin="16dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

#### Attributes information

TextFieldView is extends of TextInputLayout, you can use parent attributes. Additional attributes is

##### _app:inputType_
[enum]: type of textfield: text, password, pin, phone, ktp, address, default text\

##### _app:imeOptions_
[enum]: type of action key: next, done, send, default text\

##### _app:maxLength_
[int]: max legth of input

#### Delegeta for receive input value of TextFieldView
```kotlin
var delegate: TextFieldDelegate? = null
```
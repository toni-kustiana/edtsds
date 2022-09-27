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
    implementation 'com.github.edtslib:edtslib:latest'
}
```
## List of modules
# [1. Popup](#Popup)
# [2. BoardingView](#BoardingView)
# [3. OtpVerificationView](#OtpVerificationView)
# [4. SlidingBannerView](#SlidingBannerView)

## List of components

# [1. Primary/Secondary Color](#Color)
# [2. AlertBoxView](#AlertBoxView)
# [3. BadgeView](#BadgeView)
# [4. ButtonView](#ButtonView)
# [5. TextFieldView](#TextFieldView)
# [6. OtpView](#OtpView)
# [7. StepperView](#StepperView)
# [7. RibbonView](#RibbonView)
# [8. PagingNavigationView](#PagingNavigationView)
# [9. LinkTextView](#LinkTextView)
# [10. OtpRemainingView](#OtpRemainingView)

# SlidingBannerView

![SlidingBannerView](https://i.postimg.cc/t4ycyTG1/slidingbanerrview.png)


#### Usage

```xml
    <id.co.edtslib.edtsds.list.banner.SlidingBannerView
        android:layout_marginTop="@dimen/dimen_16dp"
        android:id="@+id/exampleView"
        app:itemSpace="@dimen/dimen_8dp"
        android:paddingStart="@dimen/dimen_16dp"
        app:itemPreviewSize="32dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:itemSpace_
[dimension]: space between item

##### _app:itemPreviewSize_
[dimension]: preview size of next item

### Set data

```kotlin
    val exampleView = findViewById<SlidingBannerView>(R.id.exampleView)
        exampleView.data = listOf("https://i.postimg.cc/Z0twhtqF/banner1.png",
            "https://i.postimg.cc/Z0twhtqF/banner1.png", "https://i.postimg.cc/Z0twhtqF/banner1.png")
```


# OtpVerificationView

![OtpVerificationView](https://i.postimg.cc/g2JbJkY9/otpverification.png)

#### Usage

```xml
    <id.co.edtslib.edtsds.otpverification.OtpVerificationView
        android:id="@+id/exampleView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

### Set attribute and data

```kotlin
    val otpView = binding.otpView
    val otpRemainingView = binding.otpRemainingView
    
    var delegate: OtpVerificationDelegate? = null
```

# Popup

![Popup](https://i.postimg.cc/FKkFWsQH/popupparameters.png)

#### Usage

For show popup please call static function

### two button

```kotlin
fun show(activity: FragmentActivity, title: String?, message: String,
         positiveButton: String?, negativeButton: String?,
         positiveClickListener: OnClickListener?, negativeClickListener: OnClickListener?,
         orientation: Orientation = Orientation.Horizontal)
```
![Popup](https://i.postimg.cc/26Sj78yy/popup2button.png)

For vertical layout: Orientation.Vertical

![Popup](https://i.postimg.cc/V6zyj77F/popuptwo.png)

### one button

```kotlin
fun show(activity: FragmentActivity, title: String?, message: String,
         positiveButton: String?, positiveClickListener: OnClickListener?) {
    show(activity, title, message, positiveButton, null,
        positiveClickListener, null)
}
```
![Popup](https://i.postimg.cc/XNwGNXFS/popupone.png)

#### Parameters information

#### Method for Popup
For close popup, call the function close
```kotlin

Popup.close()
```

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
[enum]: enum of alert type: success, warning, error\
error:\
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

# BadgeView

#### Usage

```xml
<id.co.edtslib.edtsds.badge.BadgeView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:foregroundGravity="center"
    app:badgeType="important"
    app:label="Label" />
```

#### Attributes information

##### _app:label_
[string]: label text for badge

##### _app:badgeType_
[enum]: enum of badge type: primary, secondary, neutral, important

primary:\
![BadgeView]()

secondary:\
![BadgeView]()

neutral:\
![BadgeView]()

important:\
![BadgeView]()


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

TextFieldView is extends from TextInputLayout. Additional attributes are

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

# OtpView
![OtpView](https://i.postimg.cc/2SMptqgx/OtpView.png)

#### Usage

```xml
   <id.co.edtslib.edtsds.otpview.OtpView
    android:id="@+id/otpView"
    android:layout_margin="16dp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:otpShape_
[reference]: shape of input otp area, default

##### _app:pinType_
[enum]: input type of PIN
1. number: pin show number
2. password: pin show *
3. passwordWithEye: number/passwword combination with icon eye

##### _app:otpPasswordSymbol_
[string]: password symbol

##### _app:otpPasswordAnimate_
[integer]: delay time (millisecond) typed to password symbol

##### _app:otpLength_
[integer]: length of pin, default 4

##### _app:otpHintTextColor_
[color]: text color of hint, default not set

##### _app:otpHint_
[string]: text of hint, default not set


### Listener when input change
```kotlin
      var delegate: OtpDelegate? = null
```

### Methode for access otpview
```kotlin
      var isError = false
fun clear()
```

# StepperView

#### Usage

```xml
    <id.co.edtslib.edtsds.stepper.StepperView
    android:layout_margin="16dp"
    app:canInput="true"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:canInput_
[boolean]: can input manually of stepper or no, default false\

false\
![TextFieldView](https://i.postimg.cc/j5cdRV1p/stepper1.png)

true\
![TextFieldView](https://i.postimg.cc/x1VrpxxM/stepper2.png)

##### _app:minValue_
[integer]: minimum value of stepper, default 0

##### _app:maxValue_
[integer]: maximum value of stepper, default no limit

##### _app:step_
[integer]: increment step value, default 1

##### _app:value_
[integer]: default value, default 1

### Listener when value change
```kotlin
      var delegate: StepperDelegate? = null
```

# RibbonView

#### Usage

```xml
    <id.co.edtslib.edtsds.RibbonView
    android:id="@+id/errorMessageView"
    app:type="error"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:type_
[enum]: enum of alert type: success, warning, error\
error:\
![AlertBoxView](https://i.postimg.cc/3RB3D9V5/error.png)

warning:\
![AlertBoxView](https://i.postimg.cc/k5RVKrvr/warning.png)

success:\
![AlertBoxView](https://i.postimg.cc/PfYm0DZG/success.png)


#### Method for show message on the RibbonView

```kotlin

fun show(message: Int)
fun show(message: String?)
```

# PagingNavigationView
![PagerNavigationView](https://i.ibb.co/wWR6T8n/nav1.jpg)
![PagerNavigationView](https://i.ibb.co/qssTDxw/nav2.jpg)

#### Usage

```xml
    <id.co.edtslib.edtsds.pagingnavigation.PagingNavigationView
    android:id="@+id/exapmleView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

### Attributes information

##### _app:count_
[integer]: size of pager

##### _app:shape_
[integer]: shape form resource id, default

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- shape for selection -->
    <item android:state_selected="true">
        <shape android:shape="oval">
            <solid android:color="#1171D4" />
        </shape>
    </item>
    <!-- shape for defaul -->
    <item>
        <shape android:shape="oval">
            <solid android:color="#DCDEE3" />
        </shape>
    </item>
</selector>
```

##### _app:shapeSize_
[dimension]: size of shape, default 4dp

##### _app:shapeSelectedWidth_
[dimension]: size of shape if selected, default = shapeSize

##### _app:space_
[reference]: space between shape, default 8dp

### Listening for click actions on the PagingNavigation

You can set a listener to be notified when the user click the PagingNavigation. An example is shown below.

```kotlin
        val navigation = findViewById<PagingNavigation>(R.id.navigation)
navigation.delegate = object : PagingNavigationDelegate {
    override fun onSelected(position: Int) {
        Toast.makeText(this@MainActivity, "Selected Index $position",
            Toast.LENGTH_SHORT).show()
    }
}
```

### Setting the view attributes via code
For convenience, many of the PagingNavigation attributes can be set via code.
```kotlin
        // set size of pager
var count: Int = 0
```

### Method for navigation actions on the PagingNavigation


```kotlin
    // selected index of shape
var selectedIndex: Int = -1
```
# LinkTextView
![LinkTextView](https://i.ibb.co/HtKNSnB/linktextview.png)

#### Usage

```xml
    <id.co.edtslib.edtsds.LinkTextView
        app:linkColor="#ff0000"
        app:linkText="Syarat &amp; Ketentuan"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Dengan melanjutkan, kamu menyetujui Syarat &amp; Ketentuan layanan di Aplikasi."
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

### Attributes information

An example is shown below.

```xml
        app:linkColor="#ff0000"
        app:linkText="Syarat &amp; Ketentuan"
```

##### _app:linkColor_
[string]: color of link

##### _app:linkText_
[reference]: the text to be in link

### Listener for click of text link
```kotlin
        findViewById<LinkTextView>(R.id.textView).setOnClickListener {
            Toast.makeText(this, "Hello Link", Toast.LENGTH_SHORT).show()
        }
```

# OtpRemainingView
![OtpRemainingView](https://i.ibb.co/R05d6NJ/pincounterview.jpg)
![OtpRemainingView](https://i.ibb.co/2qW4FCj/pin3.jpg)

#### Usage

```xml
    <id.co.edtslib.edtsds.otpremainingview.OtpRemainingView
        android:id="@+id/exampleView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:otpRemainingText="@string/send_code_counter"
        app:otpInterval="180"
        app:otpRemainingFormat="text"
        app:otpRemainingHour="jam"
        app:otpRemainingMinute="menit"
        app:otpRemainingSecond="detik"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

### Attributes information

##### _app:otpRemainingText_
[string]: text of counter, must contains %s, which will replace with remain time, for example
```xml
<string name="send_code_counter"><![CDATA[Kirim ulang dapat dilakukan setelah <b>%s</b>.]]></string>
```

##### _app:otpInterval_
[integer]: interval time, in second

##### _app:otpRemainingFormat_
[enum]: clock: format remain hh:mm:ss, text: format remain time hour [hour lang] minute [minute lang] second psecond lang]

##### _app:otpRemainingHour_
[string]: hour wording, use if otpRemainingHour = text

##### _app:otpRemainingMinute_
[string]: minute wording, use if otpRemainingMinute = text

##### _app:otpRemainingSecond_
[string]: second wording, use if otpRemainingSecond = text

### Method for navigation actions on the OtpRemainingView
You can start count with call start method

```kotlin
    fun start() 
```

### Listening for OtpRemainingView

You can set a listener to be notified when remain time is up. An example is shown below.

```kotlin
        val otpRemainingView = findViewById<OtpRemainingView>(R.id.otpRemainingView)
        otpRemainingView.delegate = object : OtpRemainingDelegate {
            override fun onExpired() {
                Toast.makeText(this@MainActivity, "Hi, counter is expired", Toast.LENGTH_SHORT).show()
                
            }
        }
```

# BoardingView
![BoardingView](https://i.postimg.cc/dV4w1xJm/boardingview.png)

#### Usage

```xml
     <id.co.edtslib.edtsds.boarding.BoardingView
    android:id="@+id/exampleView"
    app:imageHeight="208dp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

### Attributes information
##### _app:imageHeight_
[dimension]: height of image

You can use all pagenavigation attributes fot setup page navigation view [PagingNavigationView](#PagingNavigationView)

### Data

You can fill board data list like

```kotlin

val item1 = BoardingData("ic_onboarding_1", "Belanja Mudah",
    "One stop online store yang menyediakan berbagai macam produk dalam satu Aplikasi.")
val item2 = BoardingData("ic_onboarding_2", "Beragam Varian Produk",
    "Menyediakan ribuan pilihan produk yang lengkap dengan harga terbaik dari segala kebutuhan.")

val item3 = BoardingData("ic_onboarding_3", "Banyak Promonya",
    "Nikmati beragam promo dari indomaret untuk kamu dari promo cashback hingga gratis ongkir.")

exampleView.list = listOf(item1, item2, item3)
```
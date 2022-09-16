# edtsds
android edts design system

## List of components

### 1. AlertBoxView

![AlertSuccessBoxView](https://i.ibb.co/4RwGqqY/Screen-Shot-2022-09-16-at-16-01-22.png)
![AlertSuccessBoxView](https://i.ibb.co/xJgvbHH/Screen-Shot-2022-09-16-at-16-27-08.png)
![AlertSuccessBoxView](https://i.ibb.co/Gf66CGW/Screen-Shot-2022-09-16-at-16-41-05.png)

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

##### _app:message_
[enum]: type of alert: success, warning error

##### _app:message_
[string]: message of alert

##### _app:dismissible_
[dismissible]: alert box can dismissible or not, default true

#### Method for set message on the AlertSuccessBoxView

```kotlin

var message: String? = null
```

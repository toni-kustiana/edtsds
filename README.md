# edtsds
android edts design system

## List of components

# [Link to Header](##AlertBoxView) 1. AlertBoxView
# [Link to Header](##ButtonView) 2. ButtonView


## AlertBoxView

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
[enum]: enum of alert type: success, warning error
alert:
![AlertBoxView](https://i.ibb.co/9wQNKKv/error-alert.png)

warning:
![AlertBoxView](https://i.ibb.co/QrBfXHr/warning-alert.png)

success:
![AlertBoxView](https://i.ibb.co/cQdSwt8/success-alert.png)

##### _app:message_
[string]: message of alert

##### _app:dismissible_
[dismissible]: alert box can dismissible or not, default true

#### Method for set message on the AlertBoxView

```kotlin

var message: String? = null
```

## ButtonView

#### Usage

```xml
<id.co.edtslib.ButtonView
        app:state="medium"
        app:mode="light"
        android:id="@+id/buttonView"
        android:text="@string/app_name"
        android:layout_margin="16dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:state_
[enum]: enum of button state: medium, small, default small
medium:
![ButtonView](https://i.ibb.co/p1CQ5qL/button-medium.png)

small:
![ButtonView](https://i.ibb.co/98K5XGp/button-small.png)

##### _app:mode_
[enum]: enum of button mode: dark, light, default dark


# edtsds
android edts design system

## List of components

### AlertSuccessBoxView

![AlertSuccessBoxView](https://i.ibb.co/4RwGqqY/Screen-Shot-2022-09-16-at-16-01-22.png)

#### Usage

```xml
    <id.co.edtslib.AlertSuccessBoxView
    app:dismissible="true"
    app:message="abah test"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

#### Attributes information

##### _app:message_
[message]: message of alert

##### _app:dismissible_
[dismissible]: alert box can dismissible or not, default true

#### Method for set message on the AlertSuccessBoxView

```kotlin

var message: String? = null
```

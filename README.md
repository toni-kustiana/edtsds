# edtsds
android edts design system

## List of components

### 1. AlertSuccessBoxView

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

### 2. AlertWarningBoxView

![AlertSuccessBoxView](https://i.ibb.co/xJgvbHH/Screen-Shot-2022-09-16-at-16-27-08.png)

#### Usage

```xml
    <id.co.edtslib.AlertWarningBoxView
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

### 3. AlertErrorBoxView

![AlertSuccessBoxView](https://i.ibb.co/Gf66CGW/Screen-Shot-2022-09-16-at-16-41-05.png)

#### Usage

```xml
    <id.co.edtslib.AlertErrorBoxView
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

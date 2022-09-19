# edtsds
android edts design system

## List of components

# [1. AlertBoxView](#AlertBoxView)
# [2. ButtonView](#ButtonView)

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
[enum]: enum of button variant: primary, secondary, outline, negative, default primary\

primary:\
![ButtonView](https://i.postimg.cc/8zYyCqmf/button-primary.png)

secondary:\
![ButtonView](https://i.postimg.cc/XqNKL8g0/button-secondary.png)

outline:\
![ButtonView](https://i.postimg.cc/K8TTnwyM/button-outline.png)

negative:\
![ButtonView](https://i.postimg.cc/L5zXqKP9/button-negative.png)

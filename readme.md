# 动画
## 3.0 以前的视图动画只能改变 View的视图显示位置 其本身的属性并没有改变 因此其在执行完毕动画之后 响应事件的位置依然是其动画之前的位置

> TranslateAnimation
> AlphaAnimation
> ScaleAnimation
> RotateAnimation

## 3.0 以后的属性动画就可以处理这种情况，objectAnimator
> 在使用属性动画操作响应的属性的时候 相应的属性必须有get和set方法。
常用的可以直接使用的属性动画的属性值：

- translationX 和translationY 控制View的移动
- rotation、 、rotationY 控制View  对象围绕支点进行2D和3D旋转
- scaleX、scaleY 控制View围绕其支点进行2D缩放
- pivotX、pivotY控制View对象的支点位置 围绕这个支点位置进行旋转和缩放变化处理 默认情况下 该支点就是View的中心位置
- x和y 控制View在其父容器里面最终的位置
- alpha 控制View的透明度 默认值1 不透明，0 表示完全透明

# 差值器是用来控制目标变量如何最时间变化的，比如匀速、加速、先加速后减速

```

Failed to connect to github.com port 443

建立一个github帐号(pythonsoft)之后，按照提示create a repository: test.

想把这个项目clone到本地:

git clone https://github.com/pythonsoft/test.git

出现错误：

unable to access 'https://github.com/pythonsoft/test.git: Failed to connect to github.com port 443‘

原因是没有输入指定用户名和密码：

打开终端，切换到git目录(我的是/home/steven/.git)

sudo gedit config

把url = https://github.com/pythonsoft/test.git改成url = https://用户名:密码@github.com/pythonsoft/test.git。

然后就可以了。





```



<h1>CropView - Compose</h1>
<p>The <b>CropView - Compose</b> a customizable library that allows you to easily implement image cropping functionality in your Android applications.</p>
<h2>Features</h2>
<ul>
  <li><b>Image cropping:</b> Provides a user-friendly interface to crop images within your app.</li>
  <li><b>Customizable:</b> Easily customize the crop view according to your app's design and requirements.</li>
  <li>
    <b>Aspect ratio:</b> Set a specific aspect ratio for the crop view, such as square, portrait, landscape. ( limited to grid shape for now )
</li>
  <li>
    <b>Crop overlay:</b> Display an overlay grid or shape to guide the user during the cropping process. The overlay can be customized to match your app's design and 
  you will have total control over the shape of the overlay.
</li>

</ul>
<h2>Installation</h2>
<p>To use the CropView - Compose Library in your Android project, follow these steps:</p>
<ol>
    <li>Add the Jitpack repository to your project's build.gradle file.</li>
    <pre><code class="language-groovy">repositories {
   maven { url 'https://www.jitpack.io' }
}</code></pre>
 <li>Add the library to your project's build.gradle file.
    <br/>
    <!-- version tag -->
    [![](https://www.jitpack.io/v/oguzhanaslann/CropView-Compose.svg)](https://www.jitpack.io/#oguzhanaslann/CropView-Compose)
</li>
    <pre><code class="language-groovy">dependencies {
    implementation "com.oguzhanaslann.cropview:cropview-compose:1.0.0"
}</code></pre>
  <li>Sync the project with the Gradle files to fetch the library and its dependencies.</li>
  <li>Start using the library in your code by importing the necessary classes.</li>
</ol>
<h2>Usage</h2>
<p>To use the CropView - Compose Library in your app:</p>
<pre><code class="language-kotlin">@Composable
  fun MyView() {
    Crop(
        modifier = modifier,
        drawGrid = crop,
        cropShape = cropShape
    ) {
        // your content
    }
  }</code></pre>
<p> <i>cropShape</i> above is an CropShape which is an interface that defines state of the crop shape and
the shape's view. For now, there are two implementations: 
<h3>GridCrop</h3>
<p>GridCrop is a crop shape that draws a grid on the crop view. As state, it uses a GridCropState which is a class that holds the state of the grid. </p>

<pre><code class="language-kotlin">Crop(
 cropShape = rememberGridCrop()
  ...
 ) {
  ...
}</code></pre>
<p>Additionally, you can set a size ratio to <i>GridCropState</i>.</p>
<pre><code class="language-kotlin">val cropShape = rememberGridCrop()
cropState.setAspectRatio(Ratio.RATIO_16_9)
</code></pre>
<h3>CircleCrop</h3>
<p>CircleCrop is a crop shape that draws a circle on the crop view. As state, it uses a CircleCropState which is a class that holds the state of the circle. </p>
<pre><code> Crop(
    cropShape = rememberCircleCrop()
    ...
 ) {
    ...
 }</code></pre>

<p>After defining Crop view, it's content, and it's shape, the crop view will handle the rest. It will draw the shape on the view and will handle the touch events, resizing and moving the shape.
Whenever you need to crop the image, you can call <i>CropState.crop(Bitmap)</i> method to crop the image. </p>

<p><b>Note:</b> Both <i>rememberGridCrop</i> and <i>rememberCircleCrop</i> let you define a size/radius (current) and a minimum size/radius. Crop view will prevent the shape from getting smaller than the minimum size/radius.
Also, if you define a size/radius larger than the crop view's size, the shape will be drawn with the size of the crop view. </p>



<h2>Customization</h2>
<p> As Mentioned above, <i>CropShape</i> is , in fact, an interface. You can create your own crop shape by implementing this interface. The interface 
expect you to provide state of the shape and content view of the shape. It's worth mention that you will probably need to create your own state class
that implements CropShapeState interface.<i>CropState</i> mainly expects you to provide a resize function, which is called when
the user engages with the shape.</p>

<p>Also, if your shape by nature is an rectangle or Circle, you can use built-in <i>RectangleCropShapeState</i> and <i>CircularCropShapeState</i> classes.
They provide proper abstraction for their corresponding shapes and also implement <i>crop</i> function for you. </p>  

<h2>Examples</h2>
<p>You can find a sample app in <a href="https://github.com/oguzhanaslann/Andromedia">Andromedia</a> repository.</p>
<h2>Contribution</h2>
<p>Contributions to the Android Image Crop View Library are welcome! If you encounter any bugs, have feature requests, or would like to contribute improvements feel free to create an issue or open a pull request. If you would like to contribute.</p>
<h2>License</h2>
<p>The CropView - Compose Library is released under the <a href="https://github.com/oguzhanaslann/CropView-Compose/blob/master/LICENSE">MIT License</a>. Please review the license file for more information.</p>
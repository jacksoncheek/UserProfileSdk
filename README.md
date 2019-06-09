# UserProfileSdk

A demo application to showcase build-aware SDK builders for better Android SDK (library) design.

## Awesome Android SDK Design
### Leveraging build-aware SDK builders for better library design and keeping your Product Owners happy

So, you're building an Android SDK (library). And of course, you're going to integrate the library inside a sample application before publishing in order to test it thoroughly and use as a demo for Product and Sales team members. As you develop the library, you are bound to have Debug build variant features (SDK tooling) that you definitely do not want to ship in Production code. But, these debug features are extremely helpful for demo purposes and User Acceptance Testing (UAT).

**Let's start off with a thesis**:
Testing / Debug code should never be included in shipped Production code.

So, how do you design an SDK with all of your **awesome debug tooling** and also avoid the riskiness of shipping them "turned off" in Production code?

## Demo Application
![Demo Application](UserProfileSdkDemo.gif)

## Additional Info
[Companion blog article (draft)](https://medium.com/@jacksoncheek/intelligent-android-sdk-design-fef427604546)

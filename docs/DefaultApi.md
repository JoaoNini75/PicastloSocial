# DefaultApi

All URIs are relative to *https://virtserver.swaggerhub.com/JNINI/CIAI_Project/1.0.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create Image**](DefaultApi.md#create Image) | **POST** /image/ | Create Image
[**create Pipeline**](DefaultApi.md#create Pipeline) | **POST** /pipeline/ | Create Pipeline
[**delete Image**](DefaultApi.md#delete Image) | **DELETE** /image/{id} | Delete Image
[**delete Pipeline**](DefaultApi.md#delete Pipeline) | **DELETE** /pipeline/{id} | Delete Pipeline
[**deleteBlobId**](DefaultApi.md#deleteBlobId) | **DELETE** /blob/{id} | Delete Blob
[**deleteGroupId**](DefaultApi.md#deleteGroupId) | **DELETE** /group/{id} | Delete Group
[**deleteGroupIdMembersMemberId**](DefaultApi.md#deleteGroupIdMembersMemberId) | **DELETE** /group/{id}/members/{memberId} | Remove Group Member
[**deleteUserIdFriendsFriendId**](DefaultApi.md#deleteUserIdFriendsFriendId) | **DELETE** /user/{id}/friends/{friendId} | Remove Friendship
[**deleteUserIdPostsPostId**](DefaultApi.md#deleteUserIdPostsPostId) | **DELETE** /user/{id}/posts/{postId} | Delete Post
[**get Image**](DefaultApi.md#get Image) | **GET** /image/{id} | Get Image
[**get Pipeline**](DefaultApi.md#get Pipeline) | **GET** /pipeline/{id} | Get Pipeline
[**getBlobId**](DefaultApi.md#getBlobId) | **GET** /blob/{id} | Get Blob
[**getGroupId**](DefaultApi.md#getGroupId) | **GET** /group/{id} | Get Group
[**getUserIdPosts**](DefaultApi.md#getUserIdPosts) | **GET** /user/{id}/posts | List User Posts
[**getUserIdPostsPostId**](DefaultApi.md#getUserIdPostsPostId) | **GET** /user/{id}/posts/{postId} | Get Post
[**listGroupMembers**](DefaultApi.md#listGroupMembers) | **GET** /group/{id}/members | List Group Members
[**listUserIdFriends**](DefaultApi.md#listUserIdFriends) | **GET** /user/{id}/friends | List User Friends
[**postBlob**](DefaultApi.md#postBlob) | **POST** /blob | Create Blob
[**postGroup**](DefaultApi.md#postGroup) | **POST** /group | Create Group
[**postGroupIdMembersMemberId**](DefaultApi.md#postGroupIdMembersMemberId) | **POST** /group/{id}/members/{memberId} | Add Group Member
[**postUserIdFriendsFriendId**](DefaultApi.md#postUserIdFriendsFriendId) | **POST** /user/{id}/friends/{friendId} | Send Friendship Invite
[**postUserIdPosts**](DefaultApi.md#postUserIdPosts) | **POST** /user/{id}/posts | Create Post
[**putBlobId**](DefaultApi.md#putBlobId) | **PUT** /blob/{id} | Update Blob
[**putGroupId**](DefaultApi.md#putGroupId) | **PUT** /group/{id} | Update Group
[**putUserIdFriendsFriendId**](DefaultApi.md#putUserIdFriendsFriendId) | **PUT** /user/{id}/friends/{friendId} | Respond to Friendship Invite
[**putUserIdPostsPostId**](DefaultApi.md#putUserIdPostsPostId) | **PUT** /user/{id}/posts/{postId} | Update Post
[**update Image**](DefaultApi.md#update Image) | **PUT** /image/{id} | Update Image
[**update Pipeline**](DefaultApi.md#update Pipeline) | **PUT** /pipeline/{id} | Update Pipeline

<a name="create Image"></a>
# **create Image**
> create Image(body)

Create Image

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val body : Image =  // Image | 
try {
    apiInstance.create Image(body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#create Image")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#create Image")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Image**](Image.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="create Pipeline"></a>
# **create Pipeline**
> create Pipeline(body)

Create Pipeline

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val body : Pipeline =  // Pipeline | 
try {
    apiInstance.create Pipeline(body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#create Pipeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#create Pipeline")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Pipeline**](Pipeline.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="delete Image"></a>
# **delete Image**
> delete Image(id)

Delete Image

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    apiInstance.delete Image(id)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#delete Image")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#delete Image")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="delete Pipeline"></a>
# **delete Pipeline**
> delete Pipeline(id)

Delete Pipeline

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    apiInstance.delete Pipeline(id)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#delete Pipeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#delete Pipeline")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteBlobId"></a>
# **deleteBlobId**
> deleteBlobId(id)

Delete Blob

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    apiInstance.deleteBlobId(id)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#deleteBlobId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#deleteBlobId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteGroupId"></a>
# **deleteGroupId**
> deleteGroupId(id)

Delete Group

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    apiInstance.deleteGroupId(id)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#deleteGroupId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#deleteGroupId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteGroupIdMembersMemberId"></a>
# **deleteGroupIdMembersMemberId**
> deleteGroupIdMembersMemberId(id, memberId)

Remove Group Member

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val memberId : kotlin.Long = 789 // kotlin.Long | 
try {
    apiInstance.deleteGroupIdMembersMemberId(id, memberId)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#deleteGroupIdMembersMemberId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#deleteGroupIdMembersMemberId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **memberId** | **kotlin.Long**|  | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteUserIdFriendsFriendId"></a>
# **deleteUserIdFriendsFriendId**
> deleteUserIdFriendsFriendId(id, friendId)

Remove Friendship

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | id of the user making the request
val friendId : kotlin.Long = 789 // kotlin.Long | id of the (to be) friend of the user
try {
    apiInstance.deleteUserIdFriendsFriendId(id, friendId)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#deleteUserIdFriendsFriendId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#deleteUserIdFriendsFriendId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**| id of the user making the request | [enum: 0]
 **friendId** | **kotlin.Long**| id of the (to be) friend of the user | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteUserIdPostsPostId"></a>
# **deleteUserIdPostsPostId**
> deleteUserIdPostsPostId(id, postId)

Delete Post

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val postId : kotlin.Long = 789 // kotlin.Long | 
try {
    apiInstance.deleteUserIdPostsPostId(id, postId)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#deleteUserIdPostsPostId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#deleteUserIdPostsPostId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **postId** | **kotlin.Long**|  | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="get Image"></a>
# **get Image**
> Image get Image(id)

Get Image

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : Image = apiInstance.get Image(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#get Image")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#get Image")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

[**Image**](Image.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="get Pipeline"></a>
# **get Pipeline**
> Pipeline get Pipeline(id)

Get Pipeline

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : Pipeline = apiInstance.get Pipeline(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#get Pipeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#get Pipeline")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

[**Pipeline**](Pipeline.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlobId"></a>
# **getBlobId**
> Blob getBlobId(id)

Get Blob

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : Blob = apiInstance.getBlobId(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#getBlobId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#getBlobId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

[**Blob**](Blob.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGroupId"></a>
# **getGroupId**
> Group getGroupId(id)

Get Group

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : Group = apiInstance.getGroupId(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#getGroupId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#getGroupId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  |

### Return type

[**Group**](Group.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUserIdPosts"></a>
# **getUserIdPosts**
> kotlin.Array&lt;Post&gt; getUserIdPosts(id)

List User Posts

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : kotlin.Array<Post> = apiInstance.getUserIdPosts(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#getUserIdPosts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#getUserIdPosts")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

[**kotlin.Array&lt;Post&gt;**](Post.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUserIdPostsPostId"></a>
# **getUserIdPostsPostId**
> Post getUserIdPostsPostId(id, postId)

Get Post

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val postId : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : Post = apiInstance.getUserIdPostsPostId(id, postId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#getUserIdPostsPostId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#getUserIdPostsPostId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **postId** | **kotlin.Long**|  | [enum: 0]

### Return type

[**Post**](Post.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listGroupMembers"></a>
# **listGroupMembers**
> kotlin.Array&lt;User&gt; listGroupMembers(id)

List Group Members

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : kotlin.Array<User> = apiInstance.listGroupMembers(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#listGroupMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#listGroupMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]

### Return type

[**kotlin.Array&lt;User&gt;**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listUserIdFriends"></a>
# **listUserIdFriends**
> kotlin.Array&lt;User&gt; listUserIdFriends(id)

List User Friends

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
try {
    val result : kotlin.Array<User> = apiInstance.listUserIdFriends(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#listUserIdFriends")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#listUserIdFriends")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  |

### Return type

[**kotlin.Array&lt;User&gt;**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postBlob"></a>
# **postBlob**
> postBlob(body)

Create Blob

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val body : Blob =  // Blob | 
try {
    apiInstance.postBlob(body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#postBlob")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#postBlob")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Blob**](Blob.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postGroup"></a>
# **postGroup**
> postGroup(body)

Create Group

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val body : Group =  // Group | 
try {
    apiInstance.postGroup(body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#postGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#postGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Group**](Group.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postGroupIdMembersMemberId"></a>
# **postGroupIdMembersMemberId**
> postGroupIdMembersMemberId(id, memberId, body)

Add Group Member

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val memberId : kotlin.Long = 789 // kotlin.Long | 
val body : GroupMembership =  // GroupMembership | 
try {
    apiInstance.postGroupIdMembersMemberId(id, memberId, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#postGroupIdMembersMemberId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#postGroupIdMembersMemberId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **memberId** | **kotlin.Long**|  | [enum: 0]
 **body** | [**GroupMembership**](GroupMembership.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postUserIdFriendsFriendId"></a>
# **postUserIdFriendsFriendId**
> postUserIdFriendsFriendId(id, friendId)

Send Friendship Invite

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | id of the user making the request
val friendId : kotlin.Long = 789 // kotlin.Long | id of the (to be) friend of the user
try {
    apiInstance.postUserIdFriendsFriendId(id, friendId)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#postUserIdFriendsFriendId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#postUserIdFriendsFriendId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**| id of the user making the request | [enum: 0]
 **friendId** | **kotlin.Long**| id of the (to be) friend of the user | [enum: 0]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="postUserIdPosts"></a>
# **postUserIdPosts**
> postUserIdPosts(id, body)

Create Post

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val body : kotlin.Any =  // kotlin.Any | 
try {
    apiInstance.postUserIdPosts(id, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#postUserIdPosts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#postUserIdPosts")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **body** | [**kotlin.Any**](kotlin.Any.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/xml, application/json
 - **Accept**: Not defined

<a name="postUserIdPosts"></a>
# **postUserIdPosts**
> postUserIdPosts(id, body)

Create Post

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val body : kotlin.Any =  // kotlin.Any | 
try {
    apiInstance.postUserIdPosts(id, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#postUserIdPosts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#postUserIdPosts")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **body** | [**kotlin.Any**](kotlin.Any.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/xml, application/json
 - **Accept**: Not defined

<a name="putBlobId"></a>
# **putBlobId**
> putBlobId(id, body)

Update Blob

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val body : Blob =  // Blob | 
try {
    apiInstance.putBlobId(id, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#putBlobId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#putBlobId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **body** | [**Blob**](Blob.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putGroupId"></a>
# **putGroupId**
> putGroupId(id, body)

Update Group

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val body : Group =  // Group | 
try {
    apiInstance.putGroupId(id, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#putGroupId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#putGroupId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  |
 **body** | [**Group**](Group.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putUserIdFriendsFriendId"></a>
# **putUserIdFriendsFriendId**
> putUserIdFriendsFriendId(id, friendId, body)

Respond to Friendship Invite

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | id of the user making the request
val friendId : kotlin.Long = 789 // kotlin.Long | id of the (to be) friend of the user
val body : InviteResponse =  // InviteResponse | 
try {
    apiInstance.putUserIdFriendsFriendId(id, friendId, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#putUserIdFriendsFriendId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#putUserIdFriendsFriendId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**| id of the user making the request | [enum: 0]
 **friendId** | **kotlin.Long**| id of the (to be) friend of the user | [enum: 0]
 **body** | [**InviteResponse**](InviteResponse.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putUserIdPostsPostId"></a>
# **putUserIdPostsPostId**
> putUserIdPostsPostId(id, postId, body)

Update Post

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val postId : kotlin.Long = 789 // kotlin.Long | 
val body : Post =  // Post | 
try {
    apiInstance.putUserIdPostsPostId(id, postId, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#putUserIdPostsPostId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#putUserIdPostsPostId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **postId** | **kotlin.Long**|  | [enum: 0]
 **body** | [**Post**](Post.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="update Image"></a>
# **update Image**
> update Image(id, body)

Update Image

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val body : Image =  // Image | 
try {
    apiInstance.update Image(id, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#update Image")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#update Image")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **body** | [**Image**](Image.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="update Pipeline"></a>
# **update Pipeline**
> update Pipeline(id, body)

Update Pipeline

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
val id : kotlin.Long = 789 // kotlin.Long | 
val body : Pipeline =  // Pipeline | 
try {
    apiInstance.update Pipeline(id, body)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#update Pipeline")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#update Pipeline")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Long**|  | [enum: 0]
 **body** | [**Pipeline**](Pipeline.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


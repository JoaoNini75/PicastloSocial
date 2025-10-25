# UsersApi

All URIs are relative to *https://virtserver.swaggerhub.com/JNINI/CIAI_Project/1.0.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UsersApi.md#deleteUser) | **DELETE** /user/{id} | Delete User
[**getUser**](UsersApi.md#getUser) | **GET** /user/{id} | Get User
[**postUser**](UsersApi.md#postUser) | **POST** /user | Create User
[**putUserId**](UsersApi.md#putUserId) | **PUT** /user/{id} | Update User

<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(id)

Delete User

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UsersApi()
val id : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.deleteUser(id)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#deleteUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#deleteUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Int**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getUser"></a>
# **getUser**
> User getUser(id)

Get User

user available operations

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UsersApi()
val id : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : User = apiInstance.getUser(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#getUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#getUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Int**|  |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postUser"></a>
# **postUser**
> postUser(body)

Create User

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UsersApi()
val body : User =  // User | 
try {
    apiInstance.postUser(body)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#postUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#postUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**User**](User.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putUserId"></a>
# **putUserId**
> putUserId(id, body)

Update User

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UsersApi()
val id : kotlin.Int = 56 // kotlin.Int | 
val body : User =  // User | 
try {
    apiInstance.putUserId(id, body)
} catch (e: ClientException) {
    println("4xx response calling UsersApi#putUserId")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersApi#putUserId")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.Int**|  |
 **body** | [**User**](User.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


package org.example.userservice.presentation

import com.example.groupservice.data.dtos.GroupDTO
import com.example.groupservice.data.dtos.GroupMembershipDTO
import com.example.imageservice.data.CreateImageDTO
import com.example.imageservice.data.ImageDTO
import com.example.pipelineservice.data.CreatePipeDTO
import com.example.pipelineservice.data.PipelineDTO
import com.netflix.discovery.EurekaClient
import org.example.postservice.data.CreatePostDTO
import org.example.postservice.data.PostDTO
import org.example.userservice.application.UserApplication
import org.example.userservice.data.InviteResponse
import org.example.userservice.data.dtos.*
import org.example.userservice.resources.GroupAPI
import org.example.userservice.resources.ImageAPI
import org.example.userservice.resources.PipelineAPI
import org.example.userservice.resources.PostAPI
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
class UserController(
    var eurekaClient: EurekaClient,
    val app: UserApplication,
    val imageAPI: ImageAPI,
    val pipelineAPI: PipelineAPI,
    val postAPI: PostAPI,
    val groupAPI: GroupAPI) : UserAPI {


    @Value("\${spring.application.name}")
    var appName: String? = null


    /** Image API **/
    @GetMapping("/imageAPI/hello")
    fun helloImage(): String = imageAPI.hello()

    @GetMapping("/imageAPI")
    fun listImages(@RequestParam(required = true, defaultValue = "0") page: Int,
                   @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<ImageDTO>> = imageAPI.getImages(page, size)

    @PostMapping("/imageAPI")
    fun createImage(@RequestBody image: CreateImageDTO):
            ResponseEntity<ImageDTO> = imageAPI.createImage(image)

    @GetMapping("/imageAPI/{id}")
    fun getImageById(@PathVariable id: Long):
            ResponseEntity<ImageDTO> = imageAPI.getImageById(id)

    @PutMapping("/imageAPI/{id}")
    fun updateImageById(@PathVariable id: Long, @RequestBody image: ImageDTO):
            ResponseEntity<ImageDTO> = imageAPI.updateImageById(id, image)

    @DeleteMapping("/imageAPI/{id}")
    fun deleteImage(@PathVariable id: Long):
            ResponseEntity<ImageDTO> = imageAPI.deleteImage(id)

    @GetMapping("/imageAPI/user/{username}")
    fun getUserImages(@PathVariable username: String,
                      @RequestParam(required = true, defaultValue = "0") page: Int,
                      @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<ImageDTO>> = imageAPI.getUserImages(username, page, size)

    @GetMapping("imageAPI/ids")
    fun getImagesByIds(@RequestParam(required = true) ids: List<Long>):
            ResponseEntity<List<ImageDTO>> = imageAPI.getImagesByIds(ids)


    /** Pipeline API **/
    @GetMapping("/pipelineAPI/hello")
    fun helloPipeline(): String = pipelineAPI.hello()

    @GetMapping("/pipelineAPI")
    fun listPipelines(@RequestParam(required = true, defaultValue = "0") page: Int,
                      @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<PipelineDTO>> = pipelineAPI.getPipelines(page, size)

    @PostMapping("/pipelineAPI")
    fun createPipeline(@RequestBody pipeline: CreatePipeDTO):
            ResponseEntity<PipelineDTO> = pipelineAPI.createPipeline(pipeline)

    @GetMapping("/pipelineAPI/{id}")
    fun getPipelineById(@PathVariable id: Long):
            ResponseEntity<PipelineDTO> = pipelineAPI.getPipelineById(id)

    @PutMapping("/pipelineAPI/{id}")
    fun updatePipelineById(@PathVariable id: Long, @RequestBody pipeline: PipelineDTO):
            ResponseEntity<PipelineDTO> = pipelineAPI.updatePipelineById(id, pipeline)

    @DeleteMapping("/pipelineAPI/{id}")
    fun deletePipeline(@PathVariable id: Long):
            ResponseEntity<PipelineDTO> = pipelineAPI.deletePipeline(id)

    @GetMapping("/pipelineAPI/user/{username}")
    fun getPipelinesByUser(@PathVariable username: String,
                           @RequestParam(required = true, defaultValue = "0") page: Int,
                           @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<PipelineDTO>> = pipelineAPI.getPipelinesByUser(username, page, size)


    @GetMapping("pipelineAPI/ids")
    fun getPipelinesByIds(@RequestParam(required = true) ids: List<Long>):
            ResponseEntity<List<PipelineDTO>> = pipelineAPI.getPipelinesByIds(ids)

    /** Post API **/
    @GetMapping("/postAPI/hello")
    fun helloPost(): String = postAPI.hello()

    @GetMapping("/postAPI")
    fun getPosts(
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<PostDTO>> = postAPI.getPosts(page, size)

    @PostMapping("/postAPI")
    fun createPost(@RequestBody post: CreatePostDTO): ResponseEntity<PostDTO> = postAPI.createPost(post)

    @GetMapping("/postAPI/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostDTO> = postAPI.getPostById(id)

    @PutMapping("/postAPI/{id}")
    fun updatePostById(@PathVariable id: Long, @RequestBody post: PostDTO): ResponseEntity<PostDTO> = postAPI.updatePostById(id, post)

    @DeleteMapping("/postAPI/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<PostDTO> = postAPI.deletePost(id)

    @GetMapping("/postAPI/user/{username}")
    fun getUserPosts(
        @PathVariable username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<PostDTO>> = postAPI.getUserPosts(username, page, size)

    @GetMapping("/postAPI/friends")
    fun getFriendsPosts(
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<PostDTO>> = postAPI.getFriendsPosts(page, size)

    @GetMapping("/postAPI/group/{groupID}")
    fun getGroupPosts(
        @PathVariable groupID: Long,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<PostDTO>> = postAPI.getGroupPosts(groupID, page, size)

    @GetMapping("/postAPI/public")
    fun getPublicPosts(
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<PostDTO>> = postAPI.getPublicPosts(page, size)


    /** Group API **/
    @GetMapping("/groupAPI/hello")
    fun helloGroup(): String = groupAPI.hello()

    @DeleteMapping("/groupAPI/{id}")
    fun deleteGroupId(@PathVariable id: Long): ResponseEntity<GroupDTO> = groupAPI.deleteGroupId(id)

    @DeleteMapping("/groupAPI/{id}/members")
    fun deleteGroupMember(
        @PathVariable id: Long,
        @RequestParam username: String
    ): ResponseEntity<GroupMembershipDTO> = groupAPI.deleteGroupMember(id, username)

    @GetMapping("/groupAPI/{id}")
    fun getGroupId(@PathVariable id: Long): ResponseEntity<GroupDTO> = groupAPI.getGroupId(id)

    @GetMapping("/groupAPI/{id}/members")
    fun listGroupMembers(
        @PathVariable id: Long,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<GroupMembershipDTO>> = groupAPI.listGroupMembers(id, page, size)

    @PostMapping("/groupAPI/")
    fun createGroup(@RequestBody group: GroupDTO): ResponseEntity<GroupDTO> = groupAPI.createGroup(group)

    @PostMapping("/groupAPI/{id}/members")
    fun addMemberToGroup(
        @PathVariable id: Long,
        @RequestParam username: String
    ): ResponseEntity<GroupMembershipDTO> = groupAPI.addMemberToGroup(id, username)

    @PutMapping("/groupAPI/{id}")
    fun updateGroup(
        @PathVariable id: Long,
        @RequestBody group: GroupDTO
    ): ResponseEntity<GroupDTO> = groupAPI.updateGroup(id, group)

    @GetMapping("/groupAPI/{username}/groups")
    fun listUserGroups(
        @PathVariable username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Page<GroupMembershipDTO>> = groupAPI.listUserGroups(username, page, size)



    /** User API **/

    override fun hello(): String {
        return "Hello World! from ${eurekaClient.getApplication(appName).name}"
    }

    override fun listUsers(page: Int, size: Int): ResponseEntity<Page<UserDTO>> =
        ResponseEntity.ok(app.listUsers(page, size))

    override fun searchUsers(filter: String, page: Int, size: Int): ResponseEntity<Page<UserDTO>> =
        ResponseEntity.ok(app.searchUsers(filter, page, size))


    override fun createUser(user: UserCreateDTO): ResponseEntity<UserDTO> =
        ResponseEntity.ok(app.createUser(user))

    override fun getUser(username: String): ResponseEntity<UserDTO> =
        ResponseEntity.ok(app.getUserById(username))

    override fun updateUser(username: String, user: UserUpdateDTO): ResponseEntity<UserDTO> =
        ResponseEntity.ok(app.updateUser(username, user))

    override fun deleteUser(username: String): ResponseEntity<UserDTO> =
        ResponseEntity.ok(app.deleteUser(username))


    override fun sendFriendshipInvite(username: String, friendUsername: String): ResponseEntity<Unit> =
        ResponseEntity.ok(app.sendFriendshipInvite(username, friendUsername))

    override fun respondToFriendshipInvite(username: String, friendUsername: String,
                                          inviteResponse: InviteResponse): ResponseEntity<Unit> =
        ResponseEntity.ok(app.respondToFriendshipInvite(username, friendUsername, inviteResponse))

    override fun removeFriendship(username: String, friendUsername: String): ResponseEntity<Unit> =
        ResponseEntity.ok(app.removeFriendship(username, friendUsername))

    override fun listUserFriends(username: String, page: Int, size: Int): ResponseEntity<Page<UserDTO>> =
        ResponseEntity.ok(app.listUserFriends(username, page, size))

    override fun listUserFriendshipInvites(username: String, page: Int, size: Int): ResponseEntity<Page<UserDTO>> =
        ResponseEntity.ok(app.listUserFriendshipInvites(username, page, size))

    override fun listAllFriendships(page: Int, size: Int): ResponseEntity<Page<FriendshipDTO>> =
        ResponseEntity.ok(app.listAllFriendships(page, size))

//    @PostMapping("/login")
//    fun loginUser(@RequestBody user: UserAuthDTO) = ResponseEntity.ok(app.loginUser(user))

}
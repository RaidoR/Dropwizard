package com.dwbook.phonebook.resources;


import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.dwbook.phonebook.representations.Contact;
import com.dwbook.phonebook.representations.ContactList;
import com.sun.jersey.api.client.*;
import com.dwbook.phonebook.views.AllContactView;
import com.dwbook.phonebook.views.ContactView;

@Produces(MediaType.TEXT_HTML)
@Path("/client/")
public class ClientResource {

    private Client client;

    public ClientResource(Client client) {
        this.client = client;
    }

   @GET
    @Path("showContact")
    public ContactView showContact(@QueryParam("id") int id) {
        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);
        Contact c = contactResource.get(Contact.class);
        
        System.out.println("tere Tali! " + c);

        return new ContactView(c);
    }
   
   @GET
   @Path("showAllContact")
   public AllContactView showAllContact() {
	   
	   ContactList contacts=null;
	   
//	   for (int id=1; id<3; id++){
//	   
//	   WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);
//       Contact c = contactResource.get(Contact.class);
//       contacts.add(c);
//       System.out.println(id+ "List on: " +contacts);
//       
//	   }
       WebResource allContactResource = client.resource("http://localhost:8080/contact/all");
       System.out.println("Tere Raido! "+allContactResource);
       //Contact c = allContactResource.get(Contact.class);
       //System.out.println("tere Tali! " + c);
       System.out.println("nüüd olen siin!");
       contacts = allContactResource.get(new GenericType<ContactList>(){});
       System.out.println("nüüd olen siin! " + contacts);
//       for(int i=0; i<3; i++){
//    	   Contact c = allContactResource.get(Contact.class);
//    	   System.out.println("tere Tali! " + c);
//    	   //contacts.add(c);
//       }
//       for(int x: allContactResource.size()){
//    	   Contact c = allContactResource[x].get(Contact.class);
//    	   contacts.add(c);
//       }
       return new AllContactView(contacts);
   }

    @GET
    @Path("newContact")
    public Response newContact(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phone") String phone) {
        WebResource contactResource = client.resource("http://localhost:8080/contact");
        ClientResponse response = contactResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, new Contact(0, firstName, lastName, phone));
        if (response.getStatus() == 201) {
            // Created
            return Response.status(302).entity("The contact was created successfully! The new contact can be found at " + response.getHeaders().getFirst("Location")).build();
        } else {
            // Other Status code, indicates an error
            return Response.status(422).entity(response.getEntity(String.class)).build();
        }
    }

    @GET
    @Path("updateContact")
    public Response updateContact(@QueryParam("id") int id, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phone") String phone) {
        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);
        ClientResponse response = contactResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, new Contact(id, firstName, lastName, phone));
        if (response.getStatus() == 200) {
            // Created
            return Response.status(302).entity("The contact was updated successfully!").build();
        } else {
            // Other Status code, indicates an error
            return Response.status(422).entity(response.getEntity(String.class)).build();
        }
    }

    @GET
    @Path("deleteContact")
    public Response deleteContact(@QueryParam("id") int id) {
        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);
        contactResource.delete();
        return Response.noContent().entity("Contact was deleted!").build();
    }
}

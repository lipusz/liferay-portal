package de.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

//@Component(immediate = true, service = Application.class)
@Component(immediate = true, property = { JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/upp-service/testArray",
        JaxrsWhiteboardConstants.JAX_RS_NAME + "=TestArrayRestService" }, service = Application.class)
public class TestArrayRest extends Application {

    @Override
    public Set<Object> getSingletons() {
        return Collections.<Object>singleton(this);
    }

//    @GET
//    @Path("/one")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response one(@Context MessageContext mc) {
//
//        List<Item> list = new ArrayList<>();
//        
//        {
//            Item item = new Item();
//            item.setId("it-one");
//            item.setText("text-one");
//            list.add(item);
//        }
//        
//        Items items = new Items();
//        items.setKey("key-one");
//        items.setItems(list);
//        
//        List<Items> roots = new ArrayList<>();
//        roots.add(items);
//        
//        Root root = new Root();
//        root.setRoots(roots);
//
//        mc.put("json.array.keys", Arrays.asList("items"));
// 
//        return Response.ok().entity(root).build();
//    }
 
    @GET
    @Path("/one")
    @Produces(MediaType.APPLICATION_JSON)
    public Response one() {

        List<Item> list = new ArrayList<>();
        
        {
            Item item = new Item();
            item.setId("it-one");
            item.setText("text-one");
            list.add(item);
        }
        
        Items items = new Items();
        items.setKey("key-one");
        items.setItems(list);
        
        List<Items> roots = new ArrayList<>();
        roots.add(items);
        
        Root root = new Root();
        root.setRoots(roots);
 
        return Response.ok().entity(root).build();
    }
 
    @GET
    @Path("/two")
    @Produces(MediaType.APPLICATION_JSON)
    public Response two() {

        List<Item> list = new ArrayList<>();
        
        {
            Item item = new Item();
            item.setId("it-one");
            item.setText("text-one");
            list.add(item);
        }
        
        {
            Item item = new Item();
            item.setId("it-two");
            item.setText("text-two");
            list.add(item);
        }
        
        Items items = new Items();
        items.setKey("key-two");
        items.setItems(list);
        
        List<Items> roots = new ArrayList<>();
        roots.add(items);
        
        Root root = new Root();
        root.setRoots(roots);

        return Response.ok().entity(root).build();
    }

}

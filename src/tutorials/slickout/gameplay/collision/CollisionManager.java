package tutorials.slickout.gameplay.collision;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.SlickException;
 
import tutorials.slickout.gameplay.level.ICollidableObject;
 
/**
 * Performs basic collisions between collidable types using slick2d shapes.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class CollisionManager {
 
	// the list of objects per type
	private Map<Integer, List<ICollidableObject>> collidables;
	// the list of collisions per type
	private Map<Integer, List<Integer>> collisionsTypes;
	// the list handlers for collisions
	private Map<String, ICollisionHandler> collisionHandlers;
 
	/**
	 * Creates a new CollisionManager
	 */
	public CollisionManager(){
		collidables 		= new HashMap<Integer, List<ICollidableObject>>() ;
		collisionsTypes 	= new HashMap<Integer, List<Integer>>() ;
		collisionHandlers 	= new HashMap<String, ICollisionHandler>() ;
	}
 
	/**
	 * Adds a new object for the collision bag, from this point on this object will be checked
	 * for collisions.
	 * 
	 * @param collidable the collidable object to add
	 */
	public void addCollidable(ICollidableObject collidable){
		// obtain the entry for this type
		List<ICollidableObject> collidableList = collidables.get(collidable.getCollisionType());
 
		//if there is no entry for this type add one
		if(collidableList == null){
			collidableList = new ArrayList<ICollidableObject>();
			collidables.put(collidable.getCollisionType(), collidableList);
		}
 
		// and an entry to the list
		collidableList.add(collidable);
	}
 
	/**
	 * Removes an object from the collidable bag, from this point on the object 
	 * shall not be used for collisions.
	 * 
	 * If there is no entry for this object nothing is done.
	 * 
	 * @param collidable the collidable object to remove
	 */
	public void removeCollidable(ICollidableObject collidable){
		// obtain the entry for this type
		List<ICollidableObject> collidableList = collidables.get(collidable.getCollisionType());
 
		// if the entry exists remove the object from the list (if possible)
		if(collidableList != null){
			collidableList.remove(collidable);
		}
	}
 
	/**
	 * Adds a new collision handler to the manager. From this point on all the types
	 * concerning this handler (type1 and type2) will perform collisions.
	 * 
	 * @param handler the handler to add
	 */
	public void addHandler(ICollisionHandler handler){
		// generate the key
		String key = getKey(handler.getCollider1Type(), handler.getCollider2Type());
 
		// add the handler to the map
		collisionHandlers.put( key, handler );
 
		// add the collision type1 to type 2
		addTypesToCollision(handler.getCollider1Type(), handler.getCollider2Type());
		// add the collision type2 to type 1
		addTypesToCollision(handler.getCollider2Type(), handler.getCollider1Type());
	}
 
	private void addTypesToCollision(int type1, int type2){
		// obtain collision type entry
		List<Integer> typeCollisions = collisionsTypes.get(type1);
 
		// if there is no entry create one
		if(typeCollisions == null){
			typeCollisions = new ArrayList<Integer>();
			collisionsTypes.put(type1, typeCollisions);
		}
		// add collision to list
		typeCollisions.add(type2);
	}
 
	/**
	 * Generates a key for the two collision types in the form of
	 * <smaller type>-<bigger type>
	 * @param type1 the first type
	 * @param type2 the second type
	 * @return a string with the key in the given format
	 */
	public static String getKey(int type1, int type2){
		// generate key <smaller type>-<bigger type>
		return (type1 < type2) ? type1+"-"+type2 : type2+"-"+type1; 
	}
 
	public void processCollisions() throws SlickException{
 
		// prepare a set of all keys to collide
		Set<String> allCollisionKeys = new HashSet<String>();
 
		// prepare a list of collisions to handle
		List<CollisionData> collisions = new ArrayList<CollisionData>();
 
		Set<Integer> types = collisionsTypes.keySet();
 
		// for every type that collides
		for(Integer type : types){
			//  for each type it collides with
			List<Integer> collidesWithTypes = collisionsTypes.get(type);
 
			for(Integer collidingType : collidesWithTypes){
				// if the pair was already treated ignore it else treat it
				if( !allCollisionKeys.contains(getKey(type, collidingType)) ){
					// obtain all object of type
					List<ICollidableObject> collidableForType = collidables.get(type);
					// obtain all object of collidingtype
					List<ICollidableObject> collidableForCollidingType = collidables.get(collidingType);
					
					//test to see if there are any collidable objects of the types concerned present - only processes objects if they're present
					if(!(collidableForType == null ||collidableForCollidingType == null)){
						
					
						// for each pair from type1 -> type2 verify if the collision happens 
						for( ICollidableObject collidable : collidableForType ){
							for( ICollidableObject collidesWith : collidableForCollidingType ){
								if(collidable.isCollidingWith(collidesWith)){
									// if collision happens add the collision handler to the array
									CollisionData cd = new CollisionData();
									cd.handler = collisionHandlers.get(getKey(type, collidingType));
									cd.object1 = collidable;
									cd.object2 = collidesWith;
	 
									collisions.add(cd);
								}
							}
						}
					}
					
					// set these both types as processed
					allCollisionKeys.add(getKey(type, collidingType));
				}				
			}
		}
 
		// process all collisions
		for(CollisionData cd : collisions){
			cd.handler.performCollision(cd.object1, cd.object2);
		}
	}
 
	class CollisionData{
		public ICollisionHandler handler;
		public ICollidableObject object1;
		public ICollidableObject object2;
	}
}
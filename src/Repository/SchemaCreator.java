package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Arrays;

public class SchemaCreator {

    public static void createSchema() {
        MongoDatabase db = MongoDBConnection.getDatabase();

        if (db != null) {
            System.out.println("Connected to database: " + db.getName());

            // Drop old collections if exist
            db.getCollection("enterprises").drop();
            db.getCollection("organizations").drop();
            db.getCollection("roles").drop();
            db.getCollection("users").drop();
            db.getCollection("products").drop();
            db.getCollection("ecoScores").drop();
            db.getCollection("buyers").drop();
            db.getCollection("certifiers").drop();
            db.getCollection("shippingProviders").drop();
            
         
            // Insert Enterprises
            MongoCollection<Document> enterpriseCollection = db.getCollection("enterprises");

            Document ecomEnterprise = new Document("enterprise_id", "E001")
                    .append("enterprise_name", "E-Commerce Platform");
            Document certEnterprise = new Document("enterprise_id", "E002")
                    .append("enterprise_name", "Certification Agency");
            Document shippingEnterprise = new Document("enterprise_id", "E003")
                    .append("enterprise_name", "Shipping & Logistics");
            Document supplierEnterprise = new Document("enterprise_id", "E004")
                    .append("enterprise_name", "Supplier / Manufacturer");

            enterpriseCollection.insertMany(Arrays.asList(ecomEnterprise, certEnterprise, shippingEnterprise, supplierEnterprise));
            System.out.println(" Enterprises inserted");

            // Insert Organizations
            MongoCollection<Document> organizationCollection = db.getCollection("organizations");

            Document sellerMgmtUnit = new Document("organization_id", "O001")
                    .append("organization_name", "Seller Management Unit")
                    .append("enterprise_id", "E001");

            Document customerSupportUnit = new Document("organization_id", "O002")
                    .append("organization_name", "Customer Support Unit")
                    .append("enterprise_id", "E001");

            Document certReviewDept = new Document("organization_id", "O003")
                    .append("organization_name", "Certification Review Department")
                    .append("enterprise_id", "E002");

            Document shippingUnit = new Document("organization_id", "O004")
                    .append("organization_name", "Domestic Shipping Unit")
                    .append("enterprise_id", "E003");

            Document productMgmtUnit = new Document("organization_id", "O005")
                    .append("organization_name", "Product Management Unit")
                    .append("enterprise_id", "E004");

            organizationCollection.insertMany(Arrays.asList(sellerMgmtUnit, customerSupportUnit, certReviewDept, shippingUnit, productMgmtUnit));
            System.out.println(" Organizations inserted");

            // Insert Roles
            MongoCollection<Document> roleCollection = db.getCollection("roles");

            Document buyerRole = new Document("role_id", "R001").append("role_name", "Buyer").append("organization_id", "O001");
            Document sellerRole = new Document("role_id", "R002").append("role_name", "Seller").append("organization_id", "O001");
            Document supportSpecialistRole = new Document("role_id", "R003").append("role_name", "Support Specialist").append("organization_id", "O002");
            Document auditorRole = new Document("role_id", "R004").append("role_name", "Auditor").append("organization_id", "O003");
            Document complianceOfficerRole = new Document("role_id", "R005").append("role_name", "Compliance Officer").append("organization_id", "O003");
            Document shippingCoordinatorRole = new Document("role_id", "R006").append("role_name", "Shipping Coordinator").append("organization_id", "O004");
            Document carbonTrackerRole = new Document("role_id", "R007").append("role_name", "Carbon Tracker").append("organization_id", "O004");
            Document supplierAdminRole = new Document("role_id", "R008").append("role_name", "Supplier Admin").append("organization_id", "O005");
            Document materialTrackerRole = new Document("role_id", "R009").append("role_name", "Material Tracker").append("organization_id", "O005");

            roleCollection.insertMany(Arrays.asList(
                    buyerRole, sellerRole, supportSpecialistRole, auditorRole, complianceOfficerRole,
                    shippingCoordinatorRole, carbonTrackerRole, supplierAdminRole, materialTrackerRole
            ));
            System.out.println(" Roles inserted");

            // Insert Users
            MongoCollection<Document> userCollection = db.getCollection("users");

            Document buyerUser = new Document("user_id", "U001").append("username", "buyer_user").append("password", "buyer123").append("role_id", "R001");
            Document sellerUser = new Document("user_id", "U002").append("username", "seller_user").append("password", "seller123").append("role_id", "R002");
            Document supportUser = new Document("user_id", "U003").append("username", "support_user").append("password", "support123").append("role_id", "R003");
            Document auditorUser = new Document("user_id", "U004").append("username", "auditor_user").append("password", "auditor123").append("role_id", "R004");
            Document complianceUser = new Document("user_id", "U005").append("username", "compliance_user").append("password", "compliance123").append("role_id", "R005");
            Document shippingCoordUser = new Document("user_id", "U006").append("username", "shippingcoord_user").append("password", "shipping123").append("role_id", "R006");
            Document carbonTrackerUser = new Document("user_id", "U007").append("username", "carbontracker_user").append("password", "carbon123").append("role_id", "R007");
            Document supplierAdminUser = new Document("user_id", "U008").append("username", "supplieradmin_user").append("password", "supplier123").append("role_id", "R008");
            Document materialTrackerUser = new Document("user_id", "U009").append("username", "materialtracker_user").append("password", "material123").append("role_id", "R009");

            userCollection.insertMany(Arrays.asList(
                    buyerUser, sellerUser, supportUser, auditorUser, complianceUser,
                    shippingCoordUser, carbonTrackerUser, supplierAdminUser, materialTrackerUser
            ));
            System.out.println(" Users inserted");

            // Insert Products
            MongoCollection<Document> productCollection = db.getCollection("products");

            Document product1 = new Document("product_id", "P001")
                    .append("product_name", "Eco Friendly T-Shirt")
                    .append("description", "Made from 100% recycled fibers")
                    .append("seller_id", "U002")  // Seller user ID
                    .append("certifier_id", "U005") // Compliance officer user ID
                    .append("shipping_provider_id", "U006")  // Shipping Coordinator user ID
                    .append("ecoscore", 85);

            Document product2 = new Document("product_id", "P002")
                    .append("product_name", "Recycled Laptop Bag")
                    .append("description", "Laptop bag made from recycled plastics")
                    .append("seller_id", "U002")
                    .append("certifier_id", "U005")
                    .append("shipping_provider_id", "U006")
                    .append("ecoscore", 90);

            productCollection.insertMany(Arrays.asList(product1, product2));
            System.out.println(" Products inserted");

            System.out.println(" All dummy data inserted successfully!");

        } else {
            System.out.println(" Database connection failed!");
        }
    }
}

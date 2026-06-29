package controllers;

import data.PointDTO;
import exceptions.ValidationException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import services.DatabaseManager;
import services.PointCheckingService;

import java.math.BigDecimal;

@Path("/main")
public class PointController {

    @EJB
    PointCheckingService pointCheckingService;
    @EJB
    DatabaseManager databaseManager;

    @POST
    @Path("/check")
    public Response checkPointForm(PointDTO point, @Context SecurityContext securityContext) {
        String userLogin = securityContext.getUserPrincipal().getName();

        BigDecimal x = new BigDecimal(point.getX());
        BigDecimal y = new BigDecimal(point.getY());
        BigDecimal r = new BigDecimal(point.getR());

        try {
            return Response.ok(pointCheckingService.checkPoint(x, y, r, userLogin)).build();
        }
        catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/delete")
    public Response clearHistory(@Context SecurityContext securityContext) {
        String userLogin = securityContext.getUserPrincipal().getName();
        databaseManager.clearUserResults(userLogin);
        return Response.ok().build();
    }

    @GET
    @Path("/load")
    public Response loadHistory(@Context SecurityContext securityContext) {
        String userLogin = securityContext.getUserPrincipal().getName();
        return Response.ok(databaseManager.getResultsByUser(userLogin)).build();
    }
}
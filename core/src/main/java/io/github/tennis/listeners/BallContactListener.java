package io.github.tennis.listeners;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import io.github.tennis.Tennis;
import io.github.tennis.objects.BallObject;
import io.github.tennis.objects.TargetObject;
import io.github.tennis.objects.WallObject;
import io.github.tennis.screens.GameScreen;

public class BallContactListener implements ContactListener {

    GameScreen screen;
    BallObject ballObject;

    TargetObject targetObject;

    WallObject bottomWallObject;

    public BallContactListener(BallObject ballObject, TargetObject targetObject,
                        WallObject bottomWallObject, GameScreen screen){
        this.ballObject = ballObject;
        this.targetObject = targetObject;
        this.bottomWallObject = bottomWallObject;
        this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((a == ballObject.body && b == targetObject.body) || (b == ballObject.body && a == targetObject.body)) {
            screen.score += 1;
            screen.shouldNextRound = true;
            screen.tennis.soundManager.playScore();
        }
        if ((a == ballObject.body && b == bottomWallObject.body) || (b == ballObject.body && a == bottomWallObject.body)) {
            screen.score -= 1;
            screen.shouldNextRound = true;
            screen.tennis.soundManager.playFail();
        }
        if ((a == ballObject.body && b == screen.racketObject.body) || (b == ballObject.body && a == screen.racketObject.body)) {
            screen.tennis.soundManager.playHit();
        }




    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}

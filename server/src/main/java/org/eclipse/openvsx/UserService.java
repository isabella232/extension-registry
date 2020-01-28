/********************************************************************************
 * Copyright (c) 2019 TypeFox and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package org.eclipse.openvsx;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.eclipse.openvsx.entities.UserData;
import org.eclipse.openvsx.repositories.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    RepositoryService repositories;

    @Transactional
    public UserData updateUser(OAuth2AuthorizedClient authorizedClient, OAuth2User principal) {
        var provider = authorizedClient.getClientRegistration().getRegistrationId();
        switch (provider) {
            case "github":
                return updateGitHubUser(principal);
            default:
                throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
        }
    }

    private UserData updateGitHubUser(OAuth2User principal) {
        var user = repositories.findUser("github", principal.getName());
        if (user == null) {
            user = new UserData();
            user.setProvider("github");
            user.setProviderId(principal.getName());
            user.setLoginName(principal.getAttribute("login"));
            user.setFullName(principal.getAttribute("name"));
            user.setEmail(principal.getAttribute("email"));
            user.setAvatarUrl(principal.getAttribute("avatar_url"));
            entityManager.persist(user);
        } else {
            String loginName = principal.getAttribute("login");
            if (loginName != null && !loginName.equals(user.getLoginName()))
                user.setLoginName(loginName);
            String fullName = principal.getAttribute("name");
            if (fullName != null && !fullName.equals(user.getFullName()))
                user.setFullName(fullName);
            String email = principal.getAttribute("email");
            if (email != null && !email.equals(user.getEmail()))
                user.setEmail(email);
            String avatarUrl = principal.getAttribute("avatar_url");
            if (avatarUrl != null && !avatarUrl.equals(user.getAvatarUrl()))
                user.setAvatarUrl(avatarUrl);
        }
        return user;
    }

}